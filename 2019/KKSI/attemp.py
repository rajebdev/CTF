import requests
import re

ses = requests.session()
uri = 'http://202.148.2.243:30001/'
count = 0
while True:
    print '?time=1%02d'%count
    resp	= ses.get(uri + '?time=1%02d'%count)
    if 'KKSI' in resp.text:
        print resp.text
    if '<code>' != resp.text[:6]:
        print resp.text
    count += 1

