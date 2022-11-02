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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Cliente {

    private JFrame frame;

    static JTextField entradaCliente = new JTextField();
    static JTextArea chat = new JTextArea();
    static ServerSocket ss;
    static Socket s;
    static DataInputStream din;
    static DataOutputStream dout;
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
        frame.setBounds(100, 100, 500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        chat.setBounds(44, 47, 330, 189);
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
                        System.out.println("es direc");
                    }else {
                        System.out.println("jiji");
                        String msgout = carpeta.toString();
                        try {
                            dout.writeUTF(msgout);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }

                }
            }
        });
        selectDocument.setBounds(350, 350, 139, 43);
        frame.getContentPane().add(selectDocument);

        JButton enviar = new JButton("New button");
        enviar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String msgout = "";
                    msgout = entradaCliente.getText().trim();
                    dout.writeUTF(msgout);
                } catch (Exception e2) {
                }
            }
        });
        enviar.setBounds(322, 281, 139, 43);
        frame.getContentPane().add(enviar);

        JLabel Cliente  = new JLabel("Cliente");
        Cliente.setBounds(433, 52, 49, 14);
        frame.getContentPane().add(Cliente);

        entradaCliente.setBounds(200, 300, 100, 30);
        frame.getContentPane().add(entradaCliente);
    }
}