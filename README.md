# SMART-PAY-challenge
Here is the smart pay challenge.

After spending time reading the requirement, I've decided to work mainly for #1 MANPAN Processing and #3 Transaction Transmission. 

<img src="UI screenshot.png" height = 200/>

#1 MANPAN Processing

For ui challange, there are four main parts.

1. UI layout:

For this, I firstly spent some times using sketch to create a simple UI. By doing this it helps to build a clear layout structure. The Sketch file is also included. There are few changes when I implmented (e.g. add a result page to show result) that aren't included inside of the Sketch project since I do not have enough time to do it. Note that some naming convantion might be different with app.

I've followed the layout I created and build the same UI.

2. Handle EditText input problem

Within this application there are 4 EditText that have reuire different logic. I've made a customised editText to prevent user move the cursor; copy; paste etc. which may causing bad performance. It is just a work around and could be able to improve and use default EditText

    1. etTransaction: Once user input the amount. A dollar sign will be inserted by default. After user input and change focus, the etTransaction will check userInput. Since transaction length is 12 and it counted as cent. So the maximum is 9999999999.99. App will correct it if userinput above the amount

    2. etExpire: Once user input two digit, it will check if userinput over 12 and correct if needs. Also when user input the third digit app will add back slash to help divide month and year.

    3. etCvv: This contains 4 digit. Note that I do not have enough time to work on the reason dialog if user did not input CVV. In my mind once type continue, if no CVV inserted then it should pop up a dialog that contains different reason.

    4. etPan: Since I am not sure what is the minimum length for different type of credit card, I did not add any cheching and instead insert a space every 4 digit. Once user input over 8 digit, first 4 and last 4 remain visible and digits in the middle will be masked.

3. Implment User Interaction

In my logic user can select which MOTO Type on both main screen and transaction screen. App theme will be changed according to the different type using. Once user touch type on transaction page, the other type will be show under it and user can change at here. Could add some animation to make it vivid. 

If user change the type, only stored on file will be changed to default (yes) since other view are still useful.

4. UI Test

Have added some instramental test and comparision to test if ui and viewModel works correctly.

#3 Transaction Transmission

I think #3 is more challanging but it helps to learn domain knowledge comparing with #2 so I chose this as the second task

To better understand the task I spent sometime understanding and testing how Tlv work here are some of the material I've borrowed.

<a href="http://www.csc.villanova.edu/~tway/resources/ascii-table.html">ASCII Characters</a>
<a href="https://emvlab.org/tlvutils/">TLV Utilites</a>
<a href="https://en.wikipedia.org/wiki/Type%E2%80%93length%E2%80%93value">Type–length–value Wikipedia</a>
<a href="https://programmer.group/java-parses-tlv-format-data.html">Android class Tlv demo</a>

Note that for Android class Tlv demo I do study the code but within my project I made eveything by my own.

After study I figuered out that given Tag and Length are int whereas Value are String. I was confused when I read the instruction about this since it combines together.

Basically within my development each line is stored as a Tlv class. And once user click continue, check one by one and add each Tlv class to an ArrayList. Then pass the arrayList to utils and convert to Hex one by one. At last combine the converted Hex together to build a Tlv String.

After work out the Tlv list and tested for both way (String to List<Tlv> and List to String), I've decided to build a fake switch to process data and response. To do this I created a broadcastReceiver to listen and receive the data. (This is like the backend on real development) Then process the data by unwrapping it, add date; time; response code etc. and make a new Tlv String. Then send it back by broadcasting. 

Within transation page I've made another receiver to catch the data from fake switch. 

I do not have enough time at last so didn't make a good UI for result page. Instead I've made a RecyclerView and put data line by line to show the result. 