# Teaching-HEIGVD-RES-2016-Labo-Caesar


## Note sur notre labo :

Concernant notre cryptage caesar, nous avons d�cid� d'utiliser un d�calage compris entre 10 et 30, afin de ne pas sortir de la table ascii �tendue. Nous n'utilison donc pas de cyclicit�e.

Le principe de fonctionnement est le suivant :
* Le client se connecte et re�oit une cl� pour son prochain message.
* Le client envoit son message crypter au serveur, qui r�pond par un message non crypt� contenant "roger".
* Le serveur renvoit une cl� pour le prochain message du client.
* Le client termine la connexion avec le mot "EXIT", le serveur en recevant se message termine �galement la connexion de son c�t�.

![alt text](./images/Diagramme.jpg "Diagramme en fl�che")