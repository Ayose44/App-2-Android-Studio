# App-2-Android-Studio
This is an Android mobile phone application developed with Java, XML(design) and Sql(Sqlite) in Android Studio. The code is in 
spanish and the final result can be chosen by english or spanish in language button. For develop this app was used Intents, 
Bundles, Spinners, Sqlite Database..etc.

The app is for a Tv Series shop called "SerieShop" based on toucable panels placed in a shop where the client choose the tv serie that 
wants to buy and pay (similar as McDonald's panels). The app has a menu with a login, registration and forgot password options. 

For registration you need to write your username, password, credit card(this is just a simulation, you can put a fake
credit card number) and your favourite genre of tv shows (this is used for show you later suggested tv series depending of your favourite
genre). 

In forgot password you need to write your username and your credit card number and it will generate you a new password (that is a 
tv serie character name choosen randomly), once you get a new one you can login and change for one that you prefers in "My account" button.

Important, there 2 different ways to log in, as a User or as an Administrator (they have a different panel).

- Administrator: once you log in as an Administrator (the actually user is "Administrator" and the pass is "android1234"), it
will show you a panel where as an Administrator that you are, you can add new tv series in the shop or modify them. Also, it 
shows you how many registered users has the shop at this moment. When you add a tv serie, it will request you for the title,
if it is in offer, the genre of the tv show, the price, a description and a picture by a url(if you dont added it will show 
the logo of the shop). For modify the tv show is more or less the same process. 

- User: if you log in as a User, it will show you a User panel with some options:
  - Catalogue: it shows you the catalogue with the products and you can select if you want to see the products in offer, suggested or all
  (if there aren't any suggested product or in offer it will show you a message). Once you choose one you will see the price, the
  description, title,a quantity option where you choose how many products do you want and then you choose add if you want to add it to
  the cart or go back.
  - My account: in this option you can modify the details of your account. If you press X, the account will be deleted.
  - Cart (the cart logo): it shows you the cart with the products that you have choosen. There you can remove items pushing on them, pay
  or reset the cart. Once you pay, it shows you a message and creates you a bill in the sd card of your device as a .txt file.
  
The apk of the final result is in the folder "final apk".
