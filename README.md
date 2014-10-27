BirthdayReminder
================

please find below a sample android project which can be given as exercise to the team members. It can be completed in 1 week time. 
 
1) There are many contacts in the native contacts and the contact details can hold birthday and anniversary. 
 
2) The application will find out the birthdays of all the contacts (whose ever is available) and notifies the user so that he can send a greetings or call that contact. 
 
3) This should be done at a specified time every day. When the notification is selected, it should display a screen with the contact details and options (buttons) to place a call or send a greeting message. 
 
4) When call button is clicked, it should place a GSM call (or PTT call?) to the contact.
 
5) When Message button is clicked, it should open another screen with a predefined greeting message and buttons to send or cancel. User may edit the greeting message and send or cancel the message. 
 
6) If user clicks send message, it should send an SMS message with the greeting message to the contact. 
 
7) There may be multiple birthday on a given day in which case the notification will just indicate the number of birthdays and on click of that, it will show the list of birthday contacts. On click of each contact, it should display a screen with contact details and options to place a call or send a message as mentioned in point 3. 
 
Guideline
=========
 
1) A service will run at a predefined time to go through the native contacts to find out birthdays. 
 
2) If none of the contacts have birthday on that day, the service just stops. Otherwise, it send a notification with the count of contacts whose birthday falls on that day
 
3) On click of the notification, it shows a contact details activity with the contact details and options. If more than one contact, then an activity with list of contacts and on click of each activity, show a contact details activity
4) On the click of call button, use calling API to place a call
 
5) On click of send button, show an activity to draft a greeting message with send and cancel buttons. On send, place an SMS message using Messaging API
 
6) Follow Android coding guidelines and inline and function level code commenting during development and not as a post development activity
