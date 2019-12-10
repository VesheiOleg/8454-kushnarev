package ru.focusstart.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import ru.focusstart.contactlist.ContactList;
import ru.focusstart.controller.Facade3;
import ru.focusstart.controller.Facade4;
import ru.focusstart.controller.Facade5;
import ru.focusstart.controller.Facade6;
import ru.focusstart.encryption.Encryption;
import ru.focusstart.jsonobject.JSONObject;
import ru.focusstart.login.Login;
import ru.focusstart.message.Message;
import ru.focusstart.model.booleanproperties.BooleanProperties;
import ru.focusstart.serialization.JSONDeserialization;

public class ChatModel {
    private static ChatModel instance;
    private Socket socket;
    private SimpleBooleanProperty OnEnter;
    private SimpleBooleanProperty isConnect;
    //private SimpleStringProperty message;
    //private SimpleStringProperty serviceMessage;
    //private SimpleBooleanProperty haveMessage;
    //private JSONObject jsonObject;
    // private Login login;
    private SimpleObjectProperty<JSONObject> jsonObjectSimpleObject;
    // private Message messageFromUser;
    //private SimpleListProperty<String> nickNames;
    //private ContactList nicknames;
    BufferedReader reader;
    PrintWriter writer;

    public static ChatModel getInstance() {
        if (instance == null) {
            instance = new ChatModel();
        }
        return instance;
    }

    private ChatModel() {
        super();
        this.OnEnter = BooleanProperties.ON_ENTER.getBooleanPropertiesCreater().getBooleanProperty();
        this.isConnect = BooleanProperties.IS_CONNECT.getBooleanPropertiesCreater().getBooleanProperty();
        this.jsonObjectSimpleObject = new SimpleObjectProperty<>();
        this.jsonObjectSimpleObject.addListener(new Facade5());
        this.jsonObjectSimpleObject.addListener(new Facade6());
       /* this.message = new SimpleStringProperty("");
        this.message.addListener(new Facade5());
        this.serviceMessage = new SimpleStringProperty("");
        this.serviceMessage.addListener();*/
        //this.haveMessage = BooleanProperties.HAVE_MESSAGE.getBooleanPropertiesCreater().getBooleanProperty();
        //messageFromServer = new SimpleObjectProperty<>(new Message(""));
        //messageFromServer.addListener(new );
        //this.jsonObject = null;
        this.socket = null;
        //this.serverAddress = "";
        //this.userNickname = "";
        //nicknames = new ContactList();
        //this.messageFromServer = "";
        //this.messageFromUser = null;
        //this.nicknames = new ContactList();

    }
/*
    public boolean isConnect() {
        return isConnect;
    }*/

    /* public String getUserNickname() {
         return userNickname;
     }

     public String getServerAddress() {
         return serverAddress;
     }*/
/*
    public List<String> getNickNames() {
        return nickNames;
    }
*/
   /* public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }*/
/*
    public Login getLogin() {
        return login;
    }
    */
    /*
    public Message getMessageFromServer() {
        return messageFromServer;
    }*/
    /*
    public Message getMessageFromUser() {
        return messageFromUser;
    }
    */
    /*public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }*/
    /*
    public void setLogin(Login login) {
        this.login = login;
    }
    */
    /*
    public void setMessageFromUser(Message messageFromUser) {
        this.messageFromUser = messageFromUser;
    }*/
/*
    public void setMessageFromServer(Message messageFromServer) {
        this.messageFromServer.setValue(messageFromServer);
    }
*/
    public void enterToChat() {
        this.OnEnter.set(true);
     /*   while (!this.isConnect.get()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.getMessage();
            }
        }*/

        //return this.isConnect.get();
    }

    public void closeConnection() {
        try {
            if (socket != null) {
                socket.close();
                System.out.println("Соединение разорвано.");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void closeConnectionOnExit() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::closeConnection));
    }

    public void exitFromChat() {
        this.closeConnection();
        this.isConnect.set(false);
    }

    public void closeClient() {
        this.exitFromChat();
        System.exit(0);
    }

    public void connectToServer(Login login) throws IOException {
        Socket socket = new Socket(login.getServerAddress(), login.getPortNumber());
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream());
        this.sendEncryptionToServer(login);
        this.listenToServer();
    /*  this.isConnect.set(true);
        this.OnEnter.set(false);*/
 /*       JSONDeserialization jsonDeserialization = new JSONDeserialization();
        writer.println(jsonDeserialization.deserialize(this.login));
        writer.flush();*/
  /*      this.sendEncryptionToServer(login);
        this.listenToServer();*/
   /*     String message = "";
        while (message.isEmpty()) {
            if (reader.ready()) {
                message = reader.readLine();
                //break;
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        JSONDeserialization deserializationContactList = new JSONDeserialization();
        JSONObject jsonObject = deserializationContactList.deserialize(message);*/
        /*JSONObject*/
 /*       while (jsonObject == null) {
            jsonObject = this.getJSONObjectFromServer();
            if (jsonObject == null) {
                System.out.println("Сервер прислал лажу");
            }
        }*/


        //   this.OnEnter.addListener(new Facade3());

    }

    public void sendEncryptionToServer(Encryption encryptionObject) {
        //System.out.println("ChatModel " + this.messageFromUser);
        //JSONSerialization jsonSerialization = new JSONSerialization();
        //String str = jsonSerialization.serializeObject(this.messageFromUser);
        //System.out.println(str);
        //writer.println(jsonSerialization.serializeObject(object));
        writer.println(encryptionObject.serialize());
        writer.flush();
    }

    public JSONObject getJSONObjectFromServer() {
        String message = "";
        while (message.isEmpty()) {
            try {
                if (reader.ready()) {
                    message = reader.readLine();
                    //break;
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        JSONDeserialization deserializationContactList = new JSONDeserialization();
        return deserializationContactList.deserialize(message);
    }
/*
    public void listenToUser() {
        Thread ListenerUserThread = new Thread(() -> {
            boolean interrupted = false;
            while (!interrupted) {
                if (this.messageFromUser != null) {
                    this.sendEncryptionToServer(this.messageFromUser);
                    this.messageFromUser = null;
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    interrupted = true;
                }
            }
        });
        ListenerUserThread.start();
    }*/

    public void listenToServer() {
        Thread ListenerServerThread = new Thread(() -> {
            //boolean interrupted = false;
            while (true) {
                JSONObject jsonObject = this.getJSONObjectFromServer();
                if (jsonObject == null) {
                    System.out.println("Сервер прислал лажу");
                } else {
                    this.jsonObjectSimpleObject.set(jsonObject);
                }
            }
        });
        ListenerServerThread.start();
    }

    public void showServiceMessage(String serviceMessage) {

    }
}
