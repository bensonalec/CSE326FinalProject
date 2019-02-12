#include <QCoreApplication>
#include "listen.h"


int main(int argc, char **argv){
    
    //QCoreApplication a(argc, argv);
    
   // MyUDP client;
    
    //client.HelloUDP();

    Listen client;

    client.start_socket();

    return 0;
}