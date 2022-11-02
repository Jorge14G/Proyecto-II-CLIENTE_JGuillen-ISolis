package com.example.client;

import java.awt.EventQueue;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.plaf.FileChooserUI;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Cliente {

    private JFrame frame;

    static JTextField entradaCliente = new JTextField();
    static JTextField entradaCliente_1 = new JTextField();
    static JTextArea chat = new JTextArea();
    static ServerSocket ss;
    static Socket s;
    static DataInputStream din;
    static DataOutputStream dout;
    static JRadioButton r1= new JRadioButton("Word/Phrase");
    static JRadioButton r2= new JRadioButton();
    static JRadioButton r3= new JRadioButton();
    static JRadioButton r4= new JRadioButton();
    //static ObjectOutputStream documents;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Cliente window = new Cliente();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        try {
            s = new Socket("localhost", 1201);
            din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());
            //documents = new ObjectOutputStream(s.getOutputStream());
            String msgin = "";
            while (!msgin.equals("exit")) {
                msgin = din.readUTF();
                chat.setText(msgin);
                System.out.println(msgin);
            }
        } catch (Exception e) {
            System.out.println("XD");
        }
    }
    public Cliente() {
        initialize();
    }
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 750, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        chat.setBounds(45, 45, 330, 300);
        frame.getContentPane().add(chat);


        JButton selectDocument = new JButton("Browse");

        selectDocument.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser selectCarpeta = new JFileChooser();
                selectCarpeta.setCurrentDirectory(new File("."));
                selectCarpeta.setDialogTitle("Select the documents");
                selectCarpeta.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                selectCarpeta.setAcceptAllFileFilterUsed(false);

                if (selectCarpeta.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                    File carpeta = selectCarpeta.getSelectedFile(); //getCurrentDirectory();
                    if(carpeta.isDirectory()) {
                    }else {
                        String msgout = carpeta.toString();
                        try {
                            dout.writeUTF("2"+msgout);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }

                }
            }
        });
        selectDocument.setBounds(400, 135, 140, 40);
        frame.getContentPane().add(selectDocument);

        JButton enviar = new JButton("Search/Word");
        enviar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String msgout = "";
                    msgout = entradaCliente.getText().trim();

                    dout.writeUTF("1"+msgout);
                } catch (Exception e2) {
                }
            }
        });
        JButton enviar_2 = new JButton("Search/Phrase");
        enviar_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String msgout = "";
                    System.out.print(entradaCliente_1.getText());
                    msgout = entradaCliente_1.getText().trim();
                    dout.writeUTF("0"+msgout);
                } catch (Exception e2) {
                }
            }
        });
        enviar.setBounds(400, 45, 140, 40);
        frame.getContentPane().add(enviar);

        enviar_2.setBounds(400, 90, 140, 40);
        frame.getContentPane().add(enviar_2);


        JLabel Cliente  = new JLabel("Cliente");
        Cliente.setBounds(45, 20, 50, 15);
        frame.getContentPane().add(Cliente);

        entradaCliente.setBounds(570, 45, 150, 40);
        frame.getContentPane().add(entradaCliente);
        entradaCliente_1.setBounds(570, 90, 150, 40);
        frame.getContentPane().add(entradaCliente_1);
    }
}