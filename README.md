# SensorApp
This is a Sensor App that I'm currently doing for my research at Texas Tech University.
In this app we have two kinds of users: The normal user and the admin user. We can register both kind of users but, we have the condition that only admin users could register another admin user. For each user we will be able to access some sensors. These sensors are organized in three categories: Motion Sensors, Position Sensors and Environment Sensors. I followed the organization of the Android Documentation.

The sensors that are part of the motion category are: Accelerometer, Gyroscope, GPS, Motion Detection, Gravity Sensor, Rotation Vector and Step Counter.
The sensors that are part of the position category are: Magnetometer, Proximity Sensor and Game Rotation
The sensors that are part of the environment category are: Temperature, Ambiente Pressure, Relative Humidity and Illuminance.

The user can select the sensors, including all of them. After that, The user needs to select a rate for the acquisition of the data from the sensors. The application gives three options of rate: 0.2 seconds, 0.06 seconds and 0.02 seconds. These options of time are delimited by the maximum rate allowed by the sensors.

The user can select some methods to track. I've developed five methods until now that are: Sunlight Exposure, Altitude, Speed, No Motion and Compass.
Sunlight Exposure: This method takes the values from the illuminance sensor and for each value, return if the user was in an ambient with Sunlight exposition or not.
Altitude: This method returns the altitude(meters) of the user relative to the sea level.
Speed: This method returns the speed(meters per second) of the user.
No Motion: This method returns true if the user is in movement, otherwise it returns false. This method needs data from two sensors, the accelerometer and the gyroscope.
Compass: This method returns the information of orientation of the user relative to the magnetic north. It's necessary the accelerometer and de magnetometer to implements this.

After the user select the sensors and methods that he/she wants and the rate, He/she can start the acquisition of data, stop it, save it and in the end it's possible to share the data. The share button has a lot of option to share the data, if the user chose the email to share the data, all the log files will be alredy attached on the email body.


Goal: Until now, the user can just acquire data with the sensors and methods. The next steps are to analize these data and verify, using algorithms of artificial inteligence if we can track the activities like running, biking, walking, sleeping, no movement, watching tv, driving.


More features will be added in the future.


