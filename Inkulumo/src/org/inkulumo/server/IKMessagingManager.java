package org.inkulumo.server;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.jms.Topic;

import java.util.List;
import java.util.ArrayList;

public class IKMessagingManager {

	private List<String> topics;
	private Map<String, IKConnectionHandler> publishers;
	private Map<String, IKConnectionHandler> subscribers;
	private Map<String, List<String>> topicSubscribers;

	public IKMessagingManager() {
		topics = new ArrayList<String>();
		publishers = new HashMap<String, IKConnectionHandler>();
		subscribers = new HashMap<String, IKConnectionHandler>();
		topicSubscribers = new HashMap<String, List<String>>();
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
		return topics;
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
}
