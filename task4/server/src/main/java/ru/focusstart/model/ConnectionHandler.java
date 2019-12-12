package ru.focusstart.model;

import ru.focusstart.connection.ConnectionParameter;
import ru.focusstart.jsonobject.JSONObject;
import ru.focusstart.login.Login;
import ru.focusstart.message.*;

public interface ConnectionHandler {
    void handle(ConnectionParameter connectionParameter);
}

class ConnectionRequestHandler implements ConnectionHandler {

    @Override
    public void handle(ConnectionParameter connectionParameter) {
        JSONObject jsonObject = connectionParameter.getJsonObject();
        if (jsonObject instanceof Login) {
            Login login = (Login) jsonObject;
            ServerModel chatServer = ServerModel.getInstance();
            String userNickName = login.getUserNickname();
            if (chatServer.checkNickname(userNickName)) {
                //TODO Сформировать сервисное сообщение что логин не принят
                chatServer.sendMessage(connectionParameter.getWriter(), new NicknameRejectedServiceMessage());
            } else {
                connectionParameter.setNickname(userNickName);
                connectionParameter.setJsonObject(null);
                chatServer.addConnection(connectionParameter);
                /*
                readers.add(reader);
                writers.add(writer);
                clients.add(clientSocket);*/
                //TODO Сформировать сервисное сообщение что логин принят
                chatServer.sendMessage(connectionParameter.getWriter(), new NicknameAcceptedServiceMessage());
                //TODO Сформировать сообщение что контакт вошёл в чат
                chatServer.sendMessageToEveryone(new Message(userNickName + " присоединился к чату"));
                //TODO Отправить всем список контактов
                chatServer.sendMessageToEveryone(chatServer.getContactList());
            }
        }
    }
}

class MessageExchangeHandler implements ConnectionHandler {

    @Override
    public void handle(ConnectionParameter connectionParameter) {
        JSONObject jsonObject = connectionParameter.getJsonObject();
        if (jsonObject instanceof Message) {
            Message message = (Message) jsonObject;
            if (!message.getText().isEmpty()) {
                ServerModel chatServer = ServerModel.getInstance();
                chatServer.sendMessageToEveryone(message);
                //TODO Сформировать сообщение что сообщение отправлено
                chatServer.sendMessage(connectionParameter.getWriter(), new MessageDeliveredServiceMessage());
            }
            connectionParameter.setJsonObject(null);
        }
    }
}

class LogoutHandler implements ConnectionHandler {

    @Override
    public void handle(ConnectionParameter connectionParameter) {
        JSONObject jsonObject = connectionParameter.getJsonObject();
        if (jsonObject instanceof LogoutServiceMessage) {
            LogoutServiceMessage message = (LogoutServiceMessage) jsonObject;
            if (!message.getText().isEmpty()) {
                ServerModel chatServer = ServerModel.getInstance();
                String userNickname = connectionParameter.getNickname();
                chatServer.removeConnection(connectionParameter);
                chatServer.sendMessageToEveryone(new Message(userNickname + " покинул чат"));
            }
        }
    }
}