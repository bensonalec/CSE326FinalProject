#include <QUdpSocket>






Reciever::Reciever{

    QUdpSocket socket = new QUdpSocket();
    socket->bind(QHostAddress::Localhost, 5000);

    connect(udpSocket, SIGNAL(readyRead()),
            this, SLOT(processData()));

    while(true){
        if(socket->hasPendingDatagrams){
            QNetworkDatagram gram = socket->receiveDatagram();
            //do stuff with data gram maybe start threading here if needed.
            QByteArray data = gram.data();
            std::cout << data.toStdString << endl;

        }

    }
}
void Reciever::processData(){
    //here we can read that data in.
}