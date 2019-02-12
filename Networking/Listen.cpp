#include "listen.h"
#include<iostream>
#include <QUdpSocket>
#include <QHostAddress>



void Listen::start_socket(){

    socket = new QUdpSocket(this);
    socket->bind(QHostAddress::LocalHost, 6000);
    connect(socket, SIGNAL(readyRead()), this, SLOT(listening()));
    
}

void Listen::listening(){    
    QByteArray buffer;
    buffer.resize(socket->pendingDatagramSize());
    
    QHostAddress sender;
    quint16 senderPort;



    socket->readDatagram(buffer.data(), buffer.size(), &sender, &senderPort);
    //std::cout << "Message from: " << sender.toString() << "\n"; 
    //std::cout << "Message port: " << senderPort.toString() << "\n";
    std::cout << "Message: " << buffer.data() << "\n";
}