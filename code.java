import java.io.*;
import java.util.*;
import java.lang.*;
import java.net.*;

public class code{
    
public static void main(String args[])
    {
        String file="data.csv";
        FireWall fireWall=new FireWall(file);
        System.out.println("inbound,tcp,80,192.168.1.2="+fireWall.accept_packet("inbound","tcp",80,"192.168.1.2"));
        System.out.println("inbound, udp, 53, 192.168.2.1="+fireWall.accept_packet("inbound", "udp", 53, "192.168.2.1"));
        System.out.println("outbound, tcp, 10234, 192.168.10.11="+fireWall.accept_packet("outbound", "tcp", 10234, "192.168.10.11"));
        System.out.println("inbound, tcp, 81, 192.168.1.2="+fireWall.accept_packet("inbound", "tcp", 81, "192.168.1.2"));
        System.out.println("inbound, udp, 24, 52.12.48.92="+fireWall.accept_packet("inbound", "udp", 24, "52.12.48.92"));
        System.out.println("inbound, udp, 53, 192.168.1.255="+fireWall.accept_packet("inbound", "udp", 53, "192.168.1.255"));
        System.out.println("inbound, udp, 51, 192.168.1.255="+fireWall.accept_packet("inbound", "udp", 51, "192.168.1.255"));
        System.out.println("inbound, udp, 53, 192.168.0.255="+fireWall.accept_packet("inbound", "udp", 53, "192.168.0.255"));
    }
}

interface myinterface{
    boolean accept_packet(String direction,String protocol,int port,String ip_address);
}

class Trie{
    String val;
    HashMap<String,Trie> hmap;
    Trie(String s)
    {
        val=s;
        hmap=new HashMap<>();
    }
}

class FireWall implements myinterface{

    List<String> lt=new ArrayList<>();
    String line="";
    BufferedReader br=null;
    Trie head;

    FireWall(String path)
    {
        head=new Trie(" ");

        try{
            br=new BufferedReader(new FileReader(path));
            
            while((line=br.readLine())!=null)
            {
                lt.add(line+"");
            }
            
        }
        catch(Exception e)
        {
            
        }

        Trie curr=head;

        for(String st:lt)
        {
            curr=head;
            //System.out.println(st);
            String s[]=st.split(",");
            String s1=s[0];
            String s2=s[1];
            String s3=s[2];
            String s4=s[3];

            if(curr.hmap.containsKey(s1))
            {
                curr=curr.hmap.get(s1);
            }
            else{
                Trie t=new Trie(s1);
                curr.hmap.put(s1,t);
                curr=t;
            }

            if(curr.hmap.containsKey(s2))
            {
                curr=curr.hmap.get(s2);
            }
            else{
                Trie t=new Trie(s2);
                curr.hmap.put(s2,t);
                curr=t;
            }

            if(curr.hmap.containsKey(s3))
            {
                curr=curr.hmap.get(s3);
            }
            else{
                Trie t=new Trie(s3);
                curr.hmap.put(s3,t);
                curr=t;
            }

            if(curr.hmap.containsKey(s4))
            {
                curr=curr.hmap.get(s4);
            }
            else{
                Trie t=new Trie(s4);
                curr.hmap.put(s4,t);
                curr=t;
            }
          //  System.out.println(s1+" "+s2+" "+s3+" "+s4);

            

        }

        //System.out.println(lt);
    }

    

    public boolean accept_packet(String direction,String protocol,int port,String ip_address)
    {
        Trie curr=head;
        if(curr.hmap.containsKey(direction))
        {
            curr=curr.hmap.get(direction);
        }
        else{
            return false;
        }

        if(curr.hmap.containsKey(protocol))
        {
            curr=curr.hmap.get(protocol);
        }
        else{
            return false;
        }

        if(curr.hmap.containsKey(port+""))
        {
            curr=curr.hmap.get(port+"");
        }
        else{
            int f=0;
            for(String s:curr.hmap.keySet())
            {
                if(s.contains("-"))
                {
                    String s1[]=s.split("-");
                    int low=Integer.parseInt(s1[0]);
                    int high=Integer.parseInt(s1[1]);

                    if(port>=low&&port<=high)
                    {
                        f=1;
                        curr=curr.hmap.get(s);
                        break;
                    }
                }
                else{
                    int v=Integer.parseInt(s);
                    if(v==port)
                    {
                        f=1;
                        curr=curr.hmap.get(s);
                        break;
                    }
                }
            }

            if(f==0)
            {
                return false;
            }

        }


        if(curr.hmap.containsKey(ip_address))
        {
            curr=curr.hmap.get(ip_address);
        }
        else{
            int f=0;

            for(String s:curr.hmap.keySet())
            {
                if(s.contains("-"))
                {
                    String s1[]=s.split("-");
                    String low=s1[0],high=s1[1];

                    try{
                        long mylow = ipToLong(InetAddress.getByName(low));
                        long myhigh = ipToLong(InetAddress.getByName(high));
                        long myip = ipToLong(InetAddress.getByName(ip_address));

                        if(myip >= mylow && myip <= myhigh){
                         f=1;
                         curr=curr.hmap.get(s);
                         break;
                    }
                    
                    }
                    catch(Exception e)
                    {

                    }

                    
                    

                }
                else{
                    if(s.equals(ip_address))
                    {
                        f=1;
                        curr=curr.hmap.get(s);
                        break;
                    }
                }

                if(f==0)
                {
                    return false;
                }
            }
        }

        return true;

    }

    public long ipToLong(InetAddress ip) {
        byte[] octets = ip.getAddress();
        long result = 0;
        for (byte octet : octets) {
            result <<= 8;
            result |= octet & 0xff;
        }
        return result;
    }

}