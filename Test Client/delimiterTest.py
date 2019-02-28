import random

toBuildComplex = ""
toBuildSimple = ""
source = "Snapchat"
info = "Received from OJ"
extra = "Norway Sux"
delims = ['%','^','&','|','~']
for i in range(10):
    choice = random.randint(0,4)
    chosen = delims[choice]
    toBuildSimple += chosen
    if(chosen == '%'):
        chosen = "/per"
    elif(chosen == "^"):
        chosen = "/car"
    elif(chosen == "&"):
        chosen = "/and"
    elif(chosen == "|"):
        chosen = "/pip"
    else:
        chosen = "/til"
    toBuildComplex += chosen

toSendSimple = toBuildSimple + source + toBuildSimple + info + toBuildSimple + extra
toSendComplex = toBuildComplex + source + toBuildComplex + info + toBuildComplex + extra

print(toBuildSimple)
print(toSendSimple)

print(toBuildComplex)
print(toSendComplex)