# Hassan Sharghi
#27/5/2017
# C:\Python27
#to run the code, Kafka server should be up

from kafka import KafkaProducer
from random import gauss
from time import sleep
import sys
import json

server = "localhost:9092"

## Heart rate BPM (Beat per minute)
# HR_MU = 70
# HR_SD = 20

# random.gauss(mu, sigma)
# Gaussian distribution. mu is the mean, and sigma is the standard deviation
# def getHeartRate() :
# 	return int(gauss(HR_MU, HR_SD))
# 
# 
# ## Systolic Blood Presure : mmHg
# SBP_MU = 120
# SBP_SD = 25
# 
# def getSystolicBloodPressure() :
# 	return int(gauss(SBP_MU, SBP_SD))
# 
# ## Temperature Celcius
# TEMP_MU = 37
# TEMP_SD = 0.5
# 
# def getTemperature() :
# 	return format(gauss(TEMP_MU, TEMP_SD), '.2f')


def main():

	## the topic 
	topic = sys.argv[1]

	## create a Kafka producer with json serializer
	producer = KafkaProducer(value_serializer=lambda v: json.dumps(v).encode('utf-8'),
							 bootstrap_servers=server)
	print "*** Starting measurements stream on " + server + ", topic : " + topic
	file=open('eventsToKafka.txt', 'w')

	try:
		
	    while True:
	    	#read input file and send line by line to Kafka topic
	    	f= open('events.txt', 'r')
	    	while True:
	    		
	    		#remove wildcard '\n' and convert to a list
	    		line = f.readline().strip().split()
    	  		if line == []:
    	  			f.close()
    	  			break
    	  		else:
#     	  			id = line[0]
#     	  			seqId = line[1]
#     	  			eventId = line[2]
#     	  			attnum = line[3]
#     	  			userid = line[4]
#     	  			d = line[5]  #date
#     	  			t = line[6]  #time
#     	  			role = line[7]
#     	  			location = line[8]
#     	  			action = line[9]
#     	  			res = line[10]
#     	  			patientId = line[11]    
    	  			
					meas2 = { "userid":line[4], "type" : "TEMP", "id" : line[0],"seqId" : line[1],"eventId" : line[2], "attnum" : line[3], "d" : line[5], "t" : line[6], "role" : line[7],"location" : line[8], "action" : line[9],"res" : line[10], "patientId" : line[11]}
					producer.send(topic, meas2, key = b'%s'%line[4])				
					print "Sending TEMP : %s" % (json.dumps(meas2).encode('utf-8'))				
					file.write("Sending TEMP : %s\n" % (json.dumps(meas2).encode('utf-8')))			

		sleep(1)

	except KeyboardInterrupt:
	    pass

	file.close()    
	print "\nIntercepted user interruption ..\nBlock until all pending messages are sent.."
	producer.flush()

if __name__ == "__main__":
    main()




