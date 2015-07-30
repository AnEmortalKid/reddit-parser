# reddit-parser
A page scrubber for DndBehindTheScreen pages, particularly used for the week of 10K events. 

# How it works

The basic worflow is:

1. Scrubbing data
2. Giving data to a site builder
3. Generating the index.html for a particular theme (NPCs, Locations)
4. Copy pasting that new index.html into the appropriate location in the anemortalkid.github.io /tenk directory. 
5. Committing and pushing it into the cloudspherewebothingyintertubes

## Scrubbing the data
Every reddit url we scrub has an agreed format for entries, ie:

<pre>
&lt;strong&gt;Some data - will be used as the identifier&lt;/strong&gt;
&lt;em&gt;Subdata&lt;/em&gt;
&lt;p&gt;More Data &lt;/p&gt;
&lt;hr&gt;
</pre>

The scrubber parses through these entires and generates a ScrubbedDataObject, for each one. The scrubber returns a list of these, based what was found.

## Building the Site
A SiteBuilder takes the following parameters:
* a folder where the index.html will be placed, typically src/main/resources/THEME/
* a Title text for the webpage to generate
* the reddit url from which the data came from (this is placed in an h1 tag)
* a table header for the table that will get generated (the tr th elements)
* and the List of ScrubbedDataObjects to use when populating the table

Once buildHTML is called on a basic SiteBuilder, that will create an index.html into the desired location. 

# Warnings
The code is dank and lacks comments. 

Sidenote: this was done in a "let's see if i can hack this up way" and isn't a good sample of "what to do". You can use it as a sample of "What not to do" if you'd like. 

# How to contribute
Check it out, fork it, whatever you want. Submit pull requests if you want. 



