# Online Ticketing System
**Online Ticketing System** is a web app for customers to buy film tickets online and for cinema manager to manage films, orders and users.

-------------------

## User Credentials
| Username  | Passowrd  | Role     |
| --------- | --------- | -------- |
| admin     | admin     | Admin    |
| user1     | 123456    | Customer |

## Test Scenarios
### Customer Test Scenarios(Feature B)
#### Senario 1: Register
 1. Click the `Create an account` link to register a new account.
 2. Input username, password and phone
 3. Click the `Create` button.

#### Senario 2: Log In
Use the username and password in user credentials to log in.

#### Senario 3: View All Films
See the home page or click `Movies` tab on the navigation bar.

#### Senario 4: Buy Tickets
1. Click `Buy Ticket` button on the film you are interested in to buy tickets of a film
2. Choose one schedule in the schedule selector
 - Only the schedules which start time is earlier than current time will appear in the selector
3. Choose one seat or more from the seat table
- Click an available seat to choose it
- Click a chosen seat to cancel selection
- Check the seat information and ticket information in the left information column 
4. Click `Continue` button to pay for tickets

#### Senario 5: Pay Tickets
1. Click `Continue` button in buy ticket page or click `Click here to pay` link in view order page
2. Click the `Pay` button in payment page
- The payment function is just a stub to alert a success message

#### Senario 6: Cancel Tickets
1. Click `Continue` button in buy ticket page or click `Click here to pay` link in view order page
2. Click the `Cancel` button in payment page

#### Senario 7: View Orders
Click `Orders` tab on the navigation bar

### Admin Test Scenarios(Feature A)
#### Scenario 1: Log In
Use the username and password in user credentials to log in.

#### Scenario 2: View All Films
See the home page or click `Movies` tab on the navigation bar

#### Scenario 3: Add a Film
1. Click `Add` button on the right top corner
2. Input information in every text input
- The uploaded poster will not be the real poster stored in the database

#### Scenario 4: View the information of a film
1. Click `view` button on the film you are interested in to view all the information of a film
2. Click `schedule` in the view film information page to see the schedules of the film

#### Scenario 5: Edit a film
1. Click `edit` button on the film you are interested in to edit the information of a film
2. Click `schedule` in the edit film information page to edit the schedules of the film
3. Click `submit` will only save the changes to the basic information, click `save` on schedule page only save the changes made to schedules
- Running time is not editable

#### Scenario 6: Delete a Film
Click `delete` button on the film you want to delete

#### Scenario 7: View All Orders
Click `Orders` tab on the navigation bar

#### Scenario 8: View All Users
Click `Accounts` tab on the navigation bar
