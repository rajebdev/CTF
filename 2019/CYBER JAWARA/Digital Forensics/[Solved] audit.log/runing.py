key = 'eab41dfdf73056af155aae0b6aeef933264a20edc1b6971859a60ec0d75308422193373a3206b386a7f89af83d05ed5fed'.decode('hex')
print(key)


openssl rc4-40 -d -K 7465737473 -nosalt -e -nopad -in xpl -out xpl2
