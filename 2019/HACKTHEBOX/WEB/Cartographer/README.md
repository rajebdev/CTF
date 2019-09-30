disediakan tampilan login.

<img src="1.PNG"/>

dilakukan sql injection dengan 

username  = '-
password = '


maka akan didapatkan tampilan berikut

<img src="2.PNG"/>

setelah dianalisa ternyata di bagian address trdapat method get dengan string info


setelah home di ganti dengan flag maka didapatkan flag

http://docker.hackthebox.eu:51891/panel.php?info=flag

<img src="3.PNG"/>