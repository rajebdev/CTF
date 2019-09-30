#!/bin/sh
clear
count=true
while $count
do
    KEY=`hexdump -n 16 -e '4/4 "%08X" 1 "\n"' /dev/random`
    echo $KEY
    openssl rc4-40 -d -K $KEY -nosalt -e -nopad -in "CYBER JAWARA 2019 QUALS - RULES-OF-THE-GAME.pdf.encrypted" -out "./tmp/tempRun"$KEY".pdf"
    cat "./tmp/tempRun"$KEY".pdf"
done
