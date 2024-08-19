package HW3.server;

import HW3.client.ClientController;
import HW3.dateBase.Repository;

import java.util.ArrayList;

public class ServerController {
    private boolean isServerWorking;
    private ServerView serverView;
    private ArrayList<ClientController> clientArrayList = new ArrayList<>();
    private Repository repository;

    public void setServerView(ServerView serverView) {
        this.serverView = serverView;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public boolean connectUser(ClientController clientController) {
        if (!isServerWorking) {
            return false;
        }
        clientArrayList.add(clientController);
        showOnWindow(clientController.getName() + " подключился к беседе");
        return true;

    }

    public void stopServer() {
        if (!isServerWorking) {
            showOnWindow("Сервер уже был остановлен");
        } else {
            isServerWorking = false;
            for (ClientController client : clientArrayList) {
                disconnectUser(client);
            }
            showOnWindow("Сервер остановлен!");
        }
    }

    public void startServer() {
        if (isServerWorking) {
            showOnWindow("Сервер уже был запущен");
        } else {
            isServerWorking = true;
            showOnWindow("Сервер запущен!");
        }
    }

    public String getHistory() {
        return (String) repository.readLog();
    }

    public void disconnectUser(ClientController clientController) {
//        clientArrayList.remove(clientController);
        if (clientController != null) {
            clientController.disconnectedFromServer();
            showOnWindow(clientController.getName() + " отключился от беседы");

        }
    }

    public void message(String text) {
        if (!isServerWorking) {
            return;
        }
        showOnWindow(text);
        answerAll(text);
        saveMessageInLog(text);

    }

    private void answerAll(String text) {
        for (ClientController client : clientArrayList) {
            client.answerFromServer(text);
        }
    }

    private void showOnWindow(String text) {
        serverView.showMessage(text + "\n");
    }


    private void saveMessageInLog(String text) {
        repository.save(text);
    }


}