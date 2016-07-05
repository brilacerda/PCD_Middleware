package main;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JSplitPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.DefaultCaret;

import org.inkulumo.IKEnvironment;
import org.inkulumo.message.IKTextMessage;
import org.inkulumo.session.IKTopic;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import javax.swing.JScrollPane;

public class App {
	/*
	 * JMS fields
	 */
	private Context context;
	private TopicConnectionFactory factory;
	private TopicConnection connection;
	private TopicSession session;

	/*
	 * Rooms
	 */
	final Topic roomsTopic;
	final TopicSubscriber roomsTopicSubscriber;
	final TopicPublisher roomsTopicPublisher;

	/*
	 * GUI fields
	 */
	private JFrame frame;
	private JTextField textInput;
	private JTextArea textConversation;
	private JButton btnSend;
	private DefaultListModel<String> roomsListModel = new DefaultListModel<>();

	private ConcurrentHashMap<String, List<String>> roomConversation = new ConcurrentHashMap<>();
	private ConcurrentHashMap<String, TopicSubscriber> roomSubscriber = new ConcurrentHashMap<>();
	private ConcurrentHashMap<String, TopicPublisher> roomPublisher = new ConcurrentHashMap<>();
	private String activeRoom = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App window = new App();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @throws NamingException
	 * @throws JMSException
	 */
	public App() throws NamingException, JMSException {
		initialize();

		context = new InitialContext(IKEnvironment.instance());
		factory = (TopicConnectionFactory) context.lookup("InkulumoConnectionFactory");
		connection = factory.createTopicConnection();
		session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

		// Rooms subscriber/publisher
		roomsTopic = (Topic) context.lookup(IKEnvironment.instance().get(IKEnvironment.ROOMS_TOPIC_KEY));
		roomsTopicSubscriber = session.createSubscriber(roomsTopic);
		roomsTopicPublisher = session.createPublisher(roomsTopic);

		handleNewRooms();
	}

	private void handleNewRooms() throws NamingException {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						TextMessage message = (TextMessage) roomsTopicSubscriber.receive();
						String rooms = message.getText();
						for (final String room : rooms.split("\\|")) {
							if (!roomConversation.containsKey(room)) {
								roomsListModel.addElement(room);

								Topic topic = new IKTopic(room);
								roomConversation.put(room, new ArrayList<String>());
								roomPublisher.put(room, session.createPublisher(topic));
								final TopicSubscriber subscriber = session.createSubscriber(topic);
								roomSubscriber.put(room, subscriber);

								new Thread(new Runnable() {
									@Override
									public void run() {
										TextMessage message;
										try {
											while (true) {
												message = (TextMessage) subscriber.receive();
												roomConversation.get(room).add(message.getText());
												if (room.equals(activeRoom))
													updateTextArea();
											}
										} catch (JMSException e) {
											e.printStackTrace();
										}
									}
								}).start();
							}
						}
					}
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	private void roomSelected(String room) {
		if (!room.equals(activeRoom)) {
			activeRoom = room;
			updateTextArea();
		}
	}

	private void updateTextArea() {
		StringBuilder builder = new StringBuilder();
		for (String line : roomConversation.get(activeRoom)) {
			builder.append(line);
			builder.append('\n');
		}

		textConversation.setText("");
		textConversation.setText(builder.toString());
	}

	private void sendMessage() {
		String input = textInput.getText().trim();
		if (input.isEmpty() || activeRoom == null || activeRoom.trim().isEmpty())
			return;

		try {
			roomPublisher.get(activeRoom).publish(new IKTextMessage(input, new IKTopic(activeRoom)));
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 5));

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(5, 5));

		textInput = new JTextField();
		textInput.setEnabled(false);
		textInput.setColumns(10);
		textInput.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sendMessage();
				textInput.setText("");
			}
		});
		panel.add(textInput, BorderLayout.CENTER);

		btnSend = new JButton("Send");
		btnSend.setEnabled(false);
		btnSend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sendMessage();
				textInput.setText("");
			}
		});
		panel.add(btnSend, BorderLayout.EAST);

		JSplitPane splitPane = new JSplitPane();
		frame.getContentPane().add(splitPane, BorderLayout.CENTER);

		final JList<String> listRooms = new JList<String>(roomsListModel);
		listRooms.setMinimumSize(new Dimension(100, 0));
		listRooms.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				roomSelected(listRooms.getSelectedValue());
				btnSend.setEnabled(true);
				textConversation.setEnabled(true);
				textInput.setEnabled(true);
			}
		});
		splitPane.setLeftComponent(listRooms);

		JScrollPane scrollPane = new JScrollPane();
		splitPane.setRightComponent(scrollPane);

		textConversation = new JTextArea();
		textConversation.setEnabled(false);
		textConversation.setMinimumSize(new Dimension(300, 15));
		textConversation.setLineWrap(true);
		textConversation.setEditable(false);
		((DefaultCaret) textConversation.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		scrollPane.setViewportView(textConversation);

		JToolBar toolBar = new JToolBar();
		frame.getContentPane().add(toolBar, BorderLayout.NORTH);

		JButton btnExit = new JButton("Exit");
		btnExit.setIcon(new ImageIcon(App.class.getResource("/main/icons/application-exit.png")));
		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		toolBar.add(btnExit);

		JButton btnNewRoom = new JButton("New Room");
		btnNewRoom.setIcon(new ImageIcon(App.class.getResource("/main/icons/list-add.png")));
		btnNewRoom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String newRoom = JOptionPane.showInputDialog(frame, "Pick a name!", "New Room",
							JOptionPane.QUESTION_MESSAGE);
					session.createTopic(newRoom);
				} catch (JMSException ex) {
					ex.printStackTrace();
				}
			}
		});
		toolBar.add(btnNewRoom);
	}
}
