Please read this outline text document for detail of implementation, it could be necessary to understand all functionality.

Thank you and have a great week!

Outline of Implementation

------Member can view all scheduled fitness classes-------

Press the "View All Classes" button and the Search Results area shows all classes.


------Search by Class Name of Day of the Week------

- Fill out relevant field -> Press Button corresponding to said field -> results appear in the "Search Results" section


-------Member can enroll into a fitness class------

After signing in, any class in the listview under search results (by default, after searching, at any point), if tapped on provides the option to "Enroll"
or to "Confirm". By clicking enroll, the application will try to enroll the member in the class, succeeding or failing based on checked conflicts detailed later


------Member can see a list of all fitness classes they are enrolled in and can unenroll from a fitness class------

From the landing page following sign in, if a member clicks the button "VIEW ENROLLED CLASSES" a new page is opened with a list of all classes the user is 
presently enrolled in. By clicking one of these classes in the listview, a popup is shown which displays all detail of the class. Pressing the bottom left
button from this screen labelled "UNENROLL" unenrolls the member from the respective course, and removes the course from their list of all presently enrolled
courses.


------Time Conflicts and Full Class Capacity Prevent Enrollment------

These aspects are the conflicts that are automatically checked for when a member enrolls in a course. If either conflict is found, a message is shown using Toast
which explains the relevant conflict. For example, if the time conflicts the message "A time conflict occurred! Operation Failed." or if a class is already at
capacity "Class is Full! Operation Failed.". Additionally, if the member is already enrolled, the messsage "Already enrolled, cannot enroll again." appears.


------Instructor can view a list of all enrolled gym members------

This was implemented within the Instructor Role abilities. An instructor signs in, then from the landing page clicks the button "MANAGE MY CLASSES" which opens a
new page with a listview of all the signed in instructors classes. By tapping on any class, the user is given the option to edit any of the class info, to
remove the class, or to view all enrolled members by clicking "VIEW ENROLLED". This opens a new page which shows a listview of all member currently enrolled in
the selected course. The previous page can then be returned to using the default android back button on the bottom shelf.
