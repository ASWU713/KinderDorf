KinderDorf - README
===
​
## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
​
## Overview
### Description
A babysitting app to establish a village in our digital world. Our goal is to create a group or community that will work together to help raise each other's children like we have done since the beginning of time. Overall users can create a shared group and each person gets a set number of babysitting credits. Using a shared event calendar the group collaborates to fill babysitting needs or requests through exchange of credits.
​
### App Evaluation
- **Category:** Social Networking / Communication
- **Mobile:** This app would be primarily developed for mobile but would perhaps be just as viable on a computer, such as tinder or other similar apps. Functionality wouldn't be limited to mobile devices, however mobile version could potentially have more features.
- **Story:** Allows users to create a trusted community network to more easily schedule babysitting and other related services
- **Market:** Anyone older than a teenager who are available to babysit and parents with children
- **Habit:** This app could be used as often or unoften as the user wanted depending on how deep their social life is, and what exactly they're looking for.
- **Scope:** First, we would allow users to create their trusted group of network for babysitting needs, allow users to schedule services, and then allow users to transfer funds to pay for services
​
## Product Spec
### 1. User Stories (Required and Optional)
​
**Required Must-have Stories**
​
* Users should be able to create an account and log in
* Users need to be able to join or establish a group with friends
* User should be able to schedule an event or availability 
* Users should be able to see profile
* Users should be able to see group
​

**Optional Nice-to-have Stories**
​
* Rate babysitters and kids
* Vote in new group members
* Playdate scheduler - no one expenses credits or gains
​
### 2. Screen Archetypes
* Login/Register - User signs up or logs into their account
* Home/Group Calendar - A shared calendar view where users can see various babysitting requests that are booked or open 
* Personal Calendar View -  A list of events with filter buttons on top to view All/Booked/Open babysitting requests. By clicking on an event the user will be taken to the detailed view.
* Event Details - Detailed view of a babysitting request, same view will be used for Create/Update/Delete.
* Profile View - User profile that will show user details such as name, profile pic, available credits, and transaction history.
* Group Chat View - A slack/group chat type messaging view where all users can chat.
​
### 3. Navigation
​
**Tab Navigation** (Tab to Screen)
​
- Home: Group Calendar Screen
- Calendar: Persnal Calendar view for event lists
- Profile: Profile Screen
- Chat: Group Chat Screen
​
Optional:
​
​
**Flow Navigation** (Screen to Screen)
* Forced Log-in -> Account creation if no log in is available
* Personalized/Group Posts -> Create Posts
* View Calendar -> Schedule Services
* Profile -> Text field to be modified. 
* Settings -> Toggle settings
​
## Wireframes
<img src="https://i.imgur.com/pF6ApcQ.jpg" width=750>
​
### [BONUS] Digital Wireframes & Mockups
​
​
### [BONUS] Interactive Prototype
​
## Schema 

### Models
**User**
| **Name**       | **Type** | **Description**       |
|----------------|----------|-----------------------|
| userID         | Int      | Unique ID             |
| firstName      | String   | First Name            |
| lastName       | String   | Last Name             |
| profileImage   | File     | Image File            |
| accountBalance | Float    | Credits for available |
| username       | String   | Username              |
| password       | String   | Password              |

**Transaction**
| **Name**      | **Type**  | **Description**           |
|---------------|-----------|---------------------------|
| transactionId | Int       | Unique ID                 |
| dateBuy       | Date      | Date babysitter accepted  |
| dateRequest   | Date      | Date for event            |
| balance       | Float     | Credit Usage              |
| userBuyer     | reference | User Table                |
| userSeller    | reference | User Table                |

**Chat***
| **Name**    | **Type**  | **Description** |
|-------------|-----------|-----------------|
| messageID   | Int       | Unique ID       |
| date        | Date      | Time Stamp      |
| messageText | String    | Body of message |
| userID      | reference | User Table      |

### Networking
- Login Screen:
  - Create/POST:	Create User 
  - Get/READ 	Retrieve and log in user
- Group Calendar Screen:
  - Get/READ:	Retrieve all transactions and diplay in calendar view
- Personal Calendar Screen:
  - Get/READ:	Retrieve all transactions and diplay in recycler view
- Event Details Screen:
  - Create/POST:	Create a new event to add to transaction table
  - Update/PUT:	Update an event in the transaction table
  - Delete:	Delete an event in the transaction table
- Profile Screen:
  - Get/READ:	Reads and displays data from various table
- Chat Screen:
  - Get/READ:	Read and display information from chat table
  - Create/POST:	Add to chat table
- [Create basic snippets for each Parse network request]

#### List of network requests by screen
   - Login Screen
      - (Get/READ) 	Retrieve and log in user
         ```kotlin       
        private fun loginUser(username: String, password: String) {
          ParseUser.logInInBackground(username, password, ({ user, e ->
              if (user != null) {
                  goToMainActivity()
              } else {
                  e.printStackTrace()
              }})
          )        
         }
         ```
   - Login Screen
      - Create/POST:	Create User 
         ```kotlin  
        private fun signupUser(username: String, password: String){
          val user = ParseUser()
          user.setUsername(username)
          user.setPassword(password)

          user.signUpInBackground { e ->
              if (e == null) {
                  goToMainActivity()
              } else {
                  e.printStackTrace()
              }
          }
        }   
         ```      

   - Personal Calendar Screen:
      - Get/READ:	Retrieve all transactions and diplay in recycler view   
         ```kotlin  
         fun queryappointments(){
          val query: ParseQuery<Post> = ParseQuery.getQuery(Appointment::class.java)
          query.include(Appointment.KEY_USER)
          query.addDescendingOrder("createdAt")
          query.setLimit(20)
          query.findInBackground(object: FindCallback<Post> {
              override fun done(posts: MutableList<Post>?, e: ParseException?){
                  if(e != null){
                      Log.e(TAG, "Error fetching posts")
                  }else{
                      if(posts != null){
                          adapter.clear()
                          allPosts.addAll(posts)
                          swipeContainer.setRefreshing(false)
                      }
                  }
              }
          })
         ```     
- Event Detail Screen:
  - Create/POST: Create a new event to add to transaction table
    ```kotlin
      fun createAppointments(){
          val appointment = ParseObject.create("Appointment")
          appointment.put(APPT_DATE_KEY, appointment_date)
          appointment.put(APPT_BALANCE_KEY, balance_request)
          appointment.put(USER_SELLER_KEY, ParseUser.getCurrentUser().objectId)

          query.saveInBackground(object: FindCallback<Appointment> {
              override fun done( e: ParseException?){
                  if (e == null) {
                      Toast.makeText(
                          this, "Successfully created Appointment",
                          Toast.LENGTH_SHORT
                      ).show()
                  } else {
                      Log.e(TAG, "Failed to save appointment", e)
                  }

              }
          })
      }
    ```
  - Update/PUT: Update an event in the transaction table
    ```kotlin
        fun updateAppointments() {
          val query = ParseQuery<Appointment>("Appoitment")
          try {
              val appt = query[parse_id]

              # adapt as needed
              appt.put(APPT_DATE_KEY, appointment_date)
              appt.put(APPT_BALANCE_KEY, balance_request)
              appt.put(USER_SELLER_KEY, ParseUser.getCurrentUser().objectId)
              appot.put(USER_BUYER_KEY, Parse_User_ID)
              await appt . save ()
          } catch (ParseException e) {
              e.printStackTrace()
            }
       }
    ```
  - Delete: Delete an event in the transaction table
    ```kotlin
      fun deleteAppointment() {
          val query = ParseQuery<Appointment>("Appointment")
          query.whereEqualTo("object_id", appoitment_id)
          query.findInBackground { appt, e ->
              if (e == null) {
                  appt.deleteInBackground( DeleteCallback() {
                      @Override
                      fun done (ParseException e) {
                          if (e==null) {
                              Toast.makeText(this, "Appointment Deleted..", Toast.LENGTH_SHORT).show();
                          } else {
                              Toast.makeText(this, "Appointment Failed to Deleted..", Toast.LENGTH_SHORT).show();
                          }
                      }
                  });
              } else {
                  Toast.makeText(this, "Failed to find appointment", Toast.LENGTH_SHORT).show();
              }
          }
      }
    ```
- Profile Screen
  - Get/READ: Read & display information from various tables in profile
     ```kotlin
      fun queryProfile(){
         val query: ParseQuery<User> = ParseQuery.getQuery(User::class.java)
         query.include(User.KEY_USER)
         query.include(User.firstName)
         query.include(User.lastName)
         query.include(User.profileImage)
         query.include(User.userBalance)
             query.findInBackground(object: FindCallback<User> {
             override fun done(posts: MutableList<User>?, e: ParseException?){
                 if(e != null){
                     Log.e(TAG, "Error fetching posts")
                 }else{
                     if(posts != null){
                         adapter.clear()
                         allPosts.addAll(posts)
                         swipeContainer.setRefreshing(false)
                    }
                }
            }
        })
     ```
- Chat Screen
  - Get/READ: Read & display information from Chat table 
     ```kotlin
      fun queryChat(){
         val query: ParseQuery<Chat> = ParseQuery.getChat(Chat::class.java)
         query.include(User.DATE)
         query.include(User.messageText)
         query.include(User.userID)
             query.findInBackground(object: FindCallback<Chat> {
             override fun done(posts: MutableList<Chat>?, e: ParseException?){
                 if(e != null){
                     Log.e(TAG, "Error fetching posts")
                 }else{
                     if(posts != null){
                         adapter.clear()
                         allPosts.addAll(posts)
                         swipeContainer.setRefreshing(false)
                    }
                }
            }
        })
     ```
   - Creat/POST: Add message to the Chat table 
     ```kotlin
      fun createMessage(){
          // get current date
          val date_format = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
          val cur_date = date_format.format(Date())
          //get message
          var user_message = editTextMessage.text.toString()
          val message = ParseObject.create("Message")
      
          message.put(MESSAGE_DATE, cur_date)
          message.put(USERID, userID)
          message.put(CHAT_MESSAGE_TEXT, user_message)

          query.saveInBackground(object: FindCallback<Message> {
              override fun done( e: ParseException?){
                  if (e == null) {
                      Toast.makeText(
                          this, "Successfully posted message",
                          Toast.LENGTH_SHORT
                      ).show()
                  } else {
                      Log.e(TAG, "Failed to save appointment", e)
                  }

              }
          })
      }
     ```
         
- Updates: 
  - Home Screen: <img width="256" alt="image" src="https://user-images.githubusercontent.com/33812146/163652810-2b0cd603-46ec-43d7-8beb-9fc9e73a7713.png">
  - Message Screen: <img width="256" alt="image" src="https://user-images.githubusercontent.com/33812146/163652837-70e3484b-1098-4127-8b85-5aa28e509a69.png">
  - Gif: <img src="https://media.giphy.com/media/xuQb7FaunUofCINmIE/giphy.gif" width="350">

