import tarfile
a = 1000
while a > 0:
    tf = tarfile.open("%s.tar"%a)
    tf.extractall()
    a-=1
    
