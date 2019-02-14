// listen.cpp

#include "listen.h"
#include<iostream>

Listen::Listen(QObject *parent) :
    QObject(parent)
{
    // create a QUDP socket
    socket = new QUdpSocket(this);
    
    // The most common way to use QUdpSocket class is 
    // to bind to an address and port using bind()
    // bool QAbstractSocket::bind(const QHostAddress & address, 
    //     quint16 port = 0, BindMode mode = DefaultForPlatform)
    socket->bind(QHostAddress::LocalHost, 5123);
    
    connect(socket, SIGNAL(listening()), this, SLOT(listening()));
}

void Listen::listening()
{
    // when data comes in
    QByteArray buffer;
    buffer.resize(socket->pendingDatagramSize());
    
    QHostAddress sender;
    quint16 senderPort;
    
    // qint64 QUdpSocket::readDatagram(char * data, qint64 maxSize, 
    //                 QHostAddress * address = 0, quint16 * port = 0)
    // Receives a datagram no larger than maxSize bytes and stores it in data. 
    // The sender's host address and port is stored in *address and *port 
    // (unless the pointers are 0).
    
    socket->readDatagram(buffer.data(), buffer.size(), &sender, &senderPort);
    //std::cout << "Message from: " << sender.toString() << "\n"; 
    //std::cout << "Message port: " << senderPort.toString() << "\n";
    std::cout << "Message: " << buffer.data() << "\n";
}