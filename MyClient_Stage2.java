    //Name: Japneet Sachdev
    //StudentID: 45769125
    //Stage 2 Assessment

    import java.io.*;
    import java.net.*;
    import java.util.ArrayList;
    public class MyClient_Stage2 {

      private int ServerNum;
      private static String ServerType=" ";
      private static int SerID=0;
      private static int ServerCore;
      private static int jobID;
      private static int HighestSerCount;
      private static int schedule;
      private static int minimumValue;
      private static ArrayList < String > ServerLargestList = new ArrayList < String > ();
      private static boolean haveJob = false;
      private static boolean haveJobStarted = false;
      private static int numSer;
     
     
      //commands for the client -server to print
      private static String recieve = "RCVD: ";
      private static String first = "HELO\n";
      private static String second = "REDY\n";
      private static String third = "OK\n";
      private static String fourth = "AUTH ";
      private static String fifth = "NONE";
      private static String stop = "QUIT\n";
     

      public static void main(String[] args) {
        try {
          // default IP address and port
          Socket ClientSocket = new Socket("127.0.0.1", 50000);
          DataOutputStream outCommunication = new DataOutputStream(ClientSocket.getOutputStream());
          BufferedReader inCommunication = new BufferedReader(new InputStreamReader(ClientSocket.getInputStream()));

          System.out.println("Target IP: " + ClientSocket.getInetAddress() + " Target Port: " + ClientSocket.getPort() +"\n");
          System.out.println("Connection Established" );
         
          //sending messages threeway-handshake
          outCommunication.write((first).getBytes());
          outCommunication.flush();
          String in_Com = inCommunication .readLine();
          System.out.println(in_Com);

          String username = System.getProperty("user.name");
          System.out.println("SENT: AUTH");
          outCommunication.write((fourth + username + "\n").getBytes());
          in_Com = inCommunication .readLine();
          System.out.println(recieve + in_Com);

          outCommunication.write((second).getBytes());
          outCommunication.flush();
          in_Com = inCommunication .readLine();


          //while str is not none keep runnning the loop
          while (!in_Com.equals("NONE")) {
            System.out.println(in_Com);
            System.out.println(recieve + in_Com);
            String[] jobInfo = in_Com.split(" ");
            jobID = Integer.parseInt(jobInfo[2]);

           if(jobInfo[0].equals("JOBN")){
           
           ServerLargestList.clear();
              haveJob = false;
              haveJobStarted = false;
           
           if(haveJobStarted = false){
              String GETSCAP = "GETS Avail: " + jobInfo[4] + " " + jobInfo[5] + " " + jobInfo[6] + "\n";
              outCommunication.write(GETSCAP.getBytes());
              outCommunication.flush();
            
              in_Com = inCommunication .readLine();
              System.out.println(recieve + in_Com);
              
              String[] simulator = in_Com.split(" ");
              numSer = Integer.parseInt(simulator[1]);

              outCommunication.write((third).getBytes());
              outCommunication.flush();
              
               outCommunication.write((third).getBytes());
               outCommunication.flush();
               if (numSer == 0) {
                  haveJobStarted = true;
                }
             }
                if (haveJobStarted = true) {
                outCommunication.write(("GETS Capable: " + jobInfo[4] + " " + jobInfo[5] + " " + jobInfo[6] + "\n").getBytes());
                outCommunication.flush();
                in_Com = inCommunication.readLine();
                  System.out.println(recieve + in_Com);
		
		String[] simulator = in_Com.split(" ");
               numSer = Integer.parseInt(simulator[1]);

              outCommunication.write((third).getBytes());
              outCommunication.flush();
              

              }
       
 
              if(haveJob==true){
                for (int i=0;i< numSer;i++){
                   in_Com=inCommunication.readLine();
                   }
              }else if(haveJob !=true){              
                  for (int i = 0; i < numSer; i++) {
                  in_Com = inCommunication.readLine();
                //adding the largest server to lists
                  ServerLargestList.add(in_Com);
                  String sInfo[] = in_Com.split(" ");

                  }
                }
             outCommunication.write((third).getBytes());
             outCommunication.flush();
	     in_Com=inCommunication.readLine();
             System.out.println(recieve + in_Com);
	      
	      //minimum value to compare the waiting time               
              minimumValue = 10000000;

               for (int i = 0; i < ServerLargestList.size(); i++) {
                String serverCheck[] = ServerLargestList.get(i).split(" ");
                if (Integer.parseInt(serverCheck[7]) == 0 || Integer.parseInt(serverCheck[8]) == 0) {
                  haveJob = true;
                  ServerType = serverCheck[0];
                  SerID = Integer.parseInt(serverCheck[1]);

                } else {
                  haveJob = false;
                }
              }              
              
                if (haveJob != true) {
                for (int i = 0; i < ServerLargestList.size(); i++) {
                  String serOutput[] = ServerLargestList.get(i).split(" ");
                  //EJWT used to find the wating time of the servers
                  outCommunication.write(("EJWT " + serOutput[0] + " " + serOutput[1] + "\n").getBytes());
                  outCommunication.flush();
                  in_Com = inCommunication.readLine();

		   // comparing the waiting time found with the minimum
                  if (minimumValue > (Integer.parseInt(in_Com))) {
                    minimumValue = Integer.parseInt(in_Com);
                    ServerType = serOutput[0];
                    SerID = Integer.parseInt(serOutput[1]);

                  }
                }
              }
              
              //Scheduling the algorithm
              String dispatch = "SCHD " + jobID + " " + ServerType + " " + SerID + "\n";
              outCommunication.write(dispatch.getBytes());
              outCommunication.flush();
              in_Com = inCommunication .readLine();
              System.out.println(recieve + in_Com);

              outCommunication.write((second).getBytes());
              outCommunication.flush();
              in_Com = inCommunication .readLine();
              System.out.println(in_Com);
              
            } else if (jobInfo[0].equals("JCPL")) {  // if JCPL then send REDY to ds-server
              outCommunication.write((second).getBytes());
              outCommunication.flush();
              in_Com = inCommunication .readLine();
            }
          }

          System.out.println("SENT: QUIT");
          outCommunication.write((stop).getBytes());
          in_Com = inCommunication .readLine();
          System.out.println(recieve + in_Com);

          System.out.println("Connection is terminated\n");
          inCommunication .close();
          outCommunication.close();
          ClientSocket.close();

        }catch (Exception e) {
        //error correction
          System.out.println("Something is wrong" + e);
        }
      }
    }

