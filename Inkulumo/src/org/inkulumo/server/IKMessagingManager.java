package org.inkulumo.server;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.jms.Topic;

import org.inkulumo.IKEnvironment;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;

public class IKMessagingManager {

	private List<String> topics;
	private Map<String, IKConnectionHandler> publishers;
	private Map<String, IKConnectionHandler> subscribers;
	private Map<String, List<String>> topicSubscribers;

	public IKMessagingManager() {
		publishers = new ConcurrentHashMap<String, IKConnectionHandler>();
		subscribers = new ConcurrentHashMap<String, IKConnectionHandler>();
		topicSubscribers = new ConcurrentHashMap<String, List<String>>();

		String roomsTopicName = IKEnvironment.instance().get(IKEnvironment.ROOMS_TOPIC_KEY);
		topics = new ArrayList<String>(Arrays.asList(roomsTopicName));
		topicSubscribers.put(roomsTopicName, new ArrayList<String>());
	}

	public void registerPublisher(String clientID, IKConnectionHandler handler) {
		publishers.put(clientID, handler);
	}

	public void registerSubscriber(String clientID, IKConnectionHandler handler) {
		subscribers.put(clientID, handler);
	}

	public void registerTopic(String topicName) {
		topics.add(topicName);
	}

	public List<String> getTopics() {
		return topics.subList(1, topics.size());
	}

	public Set<String> getSubscriberIDs() {
		return subscribers.keySet();
	}

	public IKConnectionHandler getSubscriberHandler(String clientID) {
		return subscribers.get(clientID);
	}

	public List<String> getTopicSubscribers(Topic topic) {
		List<String> IDs = topicSubscribers.get(topic.toString());
		return IDs == null ? new ArrayList<String>() : IDs;
	}

	public void subscribe(String clientID, Topic topic) {
		List<String> subscribers = topicSubscribers.get(topic.toString());
		if (subscribers == null)
			subscribers = new ArrayList<String>();
		subscribers.add(clientID);
		topicSubscribers.put(topic.toString(), subscribers);
	}

	public void unsubscribe(String clientID, Topic topic) {
		List<String> subscribers = topicSubscribers.get(topic.toString());
		if (subscribers != null) {
			subscribers.remove(clientID);
			topicSubscribers.put(topic.toString(), subscribers);
		}
	}
	
	public void removeHandler(IKConnectionHandler handler) {
		if (handler == null)
			return;

		for (String clientID : publishers.keySet())
			if (publishers.get(clientID) == handler)
				publishers.remove(clientID);

		for (String clientID : subscribers.keySet()) {
			if (subscribers.get(clientID) == handler) {
				subscribers.remove(clientID);
				unsubscribeFromAllTopics(clientID);
			}
		}
	}
	
	public void unsubscribeFromAllTopics(String clientID) {
		for (String topic : topicSubscribers.keySet())
			topicSubscribers.get(topic).remove(clientID);
	}
}
