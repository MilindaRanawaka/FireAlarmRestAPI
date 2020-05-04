package firesensor;

import interfaces.Login;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
        
public class RMIClient {

    private static RMIServiceInterface rmiInterface;
    

    public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
        
        System.out.println("client started");
        
        RMIClient client = new RMIClient();
        client.connectRemote();
        
        Login login = new Login();
        login.setVisible(true);
        
    }

    private void connectRemote() throws RemoteException, NotBoundException {

        Registry reg = LocateRegistry.getRegistry("localhost", 9999);
        rmiInterface =(RMIServiceInterface) reg.lookup("rmi_server");
        
        System.out.println("connectRemote excuted");
       
    }

}
