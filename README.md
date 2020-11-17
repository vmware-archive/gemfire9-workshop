<h1> VMware has ended active development of this project, this repository will no longer be updated.</h1><br>Wes Williams  
Pivotal Sr. Data Engineer  
2015-07-02 6:06 PM  
© 2015 Pivotal. All rights reserved.  

#Introduction
Pivotal GemFire is an in-memory distributed data management system providing dynamic scalability, high performance and persistence. It blends advanced techniques like replication, partitioning, data-aware routing and continuous querying.
##1. Getting Started
###1.1	Prerequisites
<p>1.1.1 Install a Java IDE to use as we will run as clients from an IDE. The examples here use STS, Spring Tool Suite, BUT you could easily use Eclipse or other IDE.
<p>1.1.2	Install JDK 1.7 on your file system. The demos are built using JDK 1.7. GemFire requires the JDK (and not just a JRE) to run gfsh commands)
<pre><code>Wes:workshop wwilliams$ java -version
java version "1.7.0_79"
Java(TM) SE Runtime Environment (build 1.7.0_79-b15)
Java HotSpot(TM) 64-Bit Server VM (build 24.79-b02, mixed mode)</code></pre>

###1.2	Installing GemFire
<p>1.2.1	Download GemFire. This workshop uses GemFire 8.1 from [network.pivotal.io](http://network.pivotal.io)
<p>1.2.2	Follow the instructions to install.
<p>1.2.3	Your install should now look like this:
<pre><code>Wes:Pivotal_GemFire_810_b50625_Linux wwilliams$ ls -l
total 88
-rw-r--r--@  1 wwilliams  wheel  43611 Aug  7 20:14 EULA.txt
drwxr-xr-x@  9 wwilliams  wheel    306 Aug 12 14:34 SampleCode
drwxr-xr-x@  7 wwilliams  wheel    238 Aug 12 14:34 bin
drwxr-xr-x@  4 wwilliams  wheel    136 Aug 12 14:33 defaultConfigs
drwxr-xr-x@  8 wwilliams  wheel    272 Aug 12 14:33 docs
drwxr-xr-x@ 18 wwilliams  wheel    612 Aug 12 14:34 dtd
drwxr-xr-x@ 51 wwilliams  wheel   1734 Aug 12 14:34 lib
drwxr-xr-x@  4 wwilliams  wheel    136 Aug 12 14:34 templates
drwxr-xr-x@  8 wwilliams  wheel    272 Aug 12 14:34 tools
Wes:Pivotal_GemFire_810_b50625_Linux wwilliams$</code></pre>
 
###1.3	Setting Up Your Environment
<p>1.3.1	Download files from github to your file system.  This workshop uses directory location at: ~/gf81/workshop.

[GemFire 8.1 Workshop](https://github.com/wwilliams-pivotal/gf81_workshop)

<p>If you downloaded the .zip file, execute the following instruction. Otherwise, git clone the above link from github)
<pre><code>wwilliams@Wes:~/gf81 $ unzip ~/Downloads/workshop.zip -d .</code></pre>
<p>1.3.2	Navigate to ~/gf81/workshop
<p>1.3.3	Update your PATH to include GemFire and the Java JDK in setenvironment.sh. For example:

<pre><code># PUT THIS INTO YOUR ~/.bash_profile since GemFire will use the variables from there

export GEMFIRE_HOME=/usr/local/Cellar/gemfire/8.1.0/libexec
export GEMFIRE=$GEMFIRE_HOME

export JAVA_HOME=$(/usr/libexec/java_home -v 1.7)

# Note: the above on OSX resolves to:
#export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.7.0_71.jdk/Contents/Home

export GF_JAVA=$JAVA_HOME/bin/java

# this gets the IP of the machine for some gemfire bind addresses
IP=$(ifconfig en0 | awk '/inet /{print substr($2,1)}' | tail -n1)
HOSTNAME=localhost</code></pre>

<p>1.3.4	This workshop uses the hostname localhost to avoid compatibility issues using localhost on some platforms. Update your /etc/hosts file to reference localhost with your IP.
<pre><code>##
# Host Database
#
# localhost is used to configure the loopback interface
# when the system is booting.  Do not change this entry.
##
127.0.0.1 	   localhost
255.255.255.255 broadcasthost
::1             localhost

192.168.1.110 localhost</code></pre>
<p>1.3.5	Open 2 separate terminal windows.
<p>1.3.6	Edit ./config/gemfire.properties to include the correct directory where extracted for the following property
<code><pre>deploy-working-dir=/Users/wwilliams/gf81/workshop/deploy</code></pre>
<p>*Note: You must use Java syntax here. You cannot use OS shortcuts like ~.*
<p>1.3.7	Proceed to the “workshop” directory and execute the package.sh script. It will copy your gemfire.properties to future chapters.
<code><pre>wwilliams@Wes:~/gf81/workshop $ . package.sh</code></pre>
