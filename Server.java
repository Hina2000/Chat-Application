
import java.net.*;
import java.io.*;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
  public class Server extends JFrame{
    ServerSocket server;
    Socket socket;
    BufferedReader br;
    PrintWriter out;
    private JLabel heading=new JLabel("Server Area");
    private JTextArea msgArea=new JTextArea();
    private JTextField msginput=new JTextField();
    private Font font=new Font("Roboto",Font.BOLD,20);

    public Server()
    {
       try{
server=new ServerSocket(7777);
System.out.println(" Server is ready to accept connection");
System.out.println("Waiting");
 socket=server.accept();
br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
out=new PrintWriter(socket.getOutputStream());
createGUI();
handelEvents();
startReading();
//startWriting();

    }
    catch(Exception e){
        System.out .println("connection closed");

    }
}
private void handelEvents() {
    msginput.addKeyListener ( new KeyListener(){

       @Override
       public void keyTyped(KeyEvent e) {
           
           
       }

       @Override
       public void keyPressed(KeyEvent e) {
       
           
       }

       @Override
       public void keyReleased(KeyEvent e) {
           
           if(e.getKeyCode()==10){
               String contentTosend=msginput.getText();
               msgArea.append("Me :" +contentTosend+"\n");
               out.println(contentTosend);
               out.flush();
               msginput.setText("");
               msginput.requestFocus();
           }
           
       }

    });



   }

private void createGUI() {
    this.setTitle("Server Messanger[END]");
    this.setSize(500,500);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    heading.setFont(font);
    msgArea.setFont(font);
    msginput.setFont(font);


    heading.setHorizontalAlignment(SwingConstants.CENTER);

    heading.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    msgArea.setEditable(false);
    msginput.setHorizontalAlignment(SwingConstants.CENTER);
    this.setLayout(new BorderLayout());
    this.add(heading,BorderLayout.NORTH);
    JScrollPane jScrollPane=new JScrollPane(msgArea);
    this.getContentPane().setBackground(Color.LIGHT_GRAY);
    this.add(jScrollPane,BorderLayout.CENTER);
    this.add(msginput,BorderLayout.SOUTH);

    this.setVisible(true);
}



public void startReading(){

Runnable r1=()-> {
    System.out.println("Reader started");
    try{
    while(true){
        
        String msg=br.readLine();
        if(msg.equals("exit")){
            System.out.println("Client terminated the chat");
            JOptionPane.showMessageDialog(this, "Server Terminated the chat");
                    msginput.setEnabled(false);
            socket.close();
            break;
        }
       // System.out.println("Client : "+msg);
       msgArea.append("Client : " +msg+ "\n");
    }
}
    catch(Exception e){
        System.exit(0);
    }
    

};
new Thread(r1).start();

}
public  void startWriting(){
    System.out.println("Writer  started");
    Runnable r2=()->{
        try{
        while(!socket.isClosed()){
            
        BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
        String content=br1.readLine();

out.println(content);
out.flush();
if(content.equals("exit")){
    socket.close();
    break;
}

            } 
        }catch(Exception e){
                System.exit(0);
            }

        
    };
new Thread(r2).start();

}



    public static void main(String args[]){
System.out.println("This is server.....going to start server");
     new Server();
    }

}