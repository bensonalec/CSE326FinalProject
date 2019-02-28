import socket
import sys

sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

server_address = ("jerry.cs.nmt.edu",5000)
sock.connect(server_address)

try:
    
    sock.sendall("bensonalec@PythonTest\tHello\n".encode("UTF-8"))
    data = sock.recv(1024)
    print(data.decode())

finally:
    print('closing socket')
    sock.close()