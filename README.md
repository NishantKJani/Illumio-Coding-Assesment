# Illumio-Coding-Assesment
Illumio-coding-assignment

# a)Coding design and Algorithms implemented:
I have used Trie data structure in order to store all the valid rules because it would help in reducing space complexity as one would just traverse those nodes which are already present no need to re-allocate memory for them and only add nodes when the data is new. Eg:-inbound,tcp,80,192.168.1.2 and inbound, tcp, 80, 192.168.1.3 are both the same upto the port number 80 so no need to provide new memory for the second rule.We can traverse the Trie upto that node (port number 80) and we don't have the child 192.168.1.3 so then we create a new child there. I have create the structure of Trie to store String value(value of node in terms of type of packet,port number,ip-address) and HashMap to store the children and retrieve data in O(1) if it exists. 
If we follow a normal approach for finding rules which match our current input and then provide as a result the output to the user then it would be very time consuming and along with that it would also consume large amount of memory space in order to store such large amount of data.

My algorithm would fetch data in O(1) if it exists which is the best case scenario but in worst case it would take O(n) as in some cases there are IP-ranges and port-ranges so I would need to traverse through those ranges in order to identify whether my given input would lie between those range or not. If yes then I would return true else false. I have used HashMap in the structure of the Trie Node as that would easily help in identifying whether the value which the user is searching is present in the current node's child or not? Else one would need to traverse each of the child values in order to obtain data.

If I had more time I would like to find out more ways in which I could reduce the worst case time complexity. A better approach through which I can find the solution if it exists in a particular interval defined by the rules because in that case only my current algorithm is getting worst case time complexity.

# b)Testing the solution:
I have tested my solution on edge cases like if the range of ip_addresses is:-192.168.1.1-192.168.2.1 then if the input ip is 192.168.1.255 then it should be validated.This seems to be normal for us but I needed to write a function which would convert a given IP address into long number format which would help me identify that the given IP address was lying in the given IP ranges.
I have also tested on scenarios where in the my given packet configuration was not part of the given rules defined so it showed false which is the expected results.

# Team Interested in:
1.Platform Team
2.Data Team
3.Policy Team
