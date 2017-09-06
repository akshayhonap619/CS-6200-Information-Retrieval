import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class File_Split {

	public static void main(String[] args) 
	{
		ArrayList<String> sent = new ArrayList<String>();
		String Intro="Migrants arriving in Finland are being offered classes on Finnish values and how to behave towards women. Concerned about a rise in the number of sexual assaults in the country, the government wants to make sure that people from very conservative cultures know what to expect in their new home. Johanna is one of those energetic, animated teachers whose cheerful energy lures even the most reluctant pupil into engaging with the lesson. She uses both her hands to stress her meaning and she always softens any difficult points with a smile. \"So in Finland,\" she says softly, \"you can't buy a wife. A woman will only be your wife if she wants to be - because here women are men's equals.\" Her pupils, all recently arrived asylum seekers at this reception centre hidden away in the snowy depths of the Finnish forest, watch her carefully - and I watch them. Some of the young Iraqi men, who already speak good English and passable Finnish, nod sagely. Others, particularly the older men, stare at one another with raised eyebrows as Johanna's words are translated into Arabic for them. One man, hunkered down inside his black ski jacket seems to be taking notes while there's a faint smile on the lips of the only headscarfed young woman in the room. Image caption Raasepoori reception centre in the Finnish forest \"But you can go out to the disco with a woman here,\" adds Johanna brightly. \"Although remember, even if she dances with you very closely and is wearing a short skirt, that doesn't mean she wants to have sex with you.\" A Somali teenager pulls his woolly hat over his ears and cradles his head in his hands as if his brain can't cope with all this new information. \"This is a very liberal country,\" he says incredulously. \"We have a lot to learn. In my country if you make sexy with a woman you are killed!\" He turns to his neighbour, a Malian man of a similar age to gauge his reaction. \"It's quite amazing,\" the Malian nods. \"In my country a woman should not go out without her husband or brother.\" Image copyright Alamy Johanna turns her attention to homosexuality and the Iraqi men on the back row - it's always the back row - begin to giggle and snigger. Find out more Listen to From Our Own Correspondent for insight and analysis from BBC journalists, correspondents and writers from around the world. Broadcast on Radio 4 on Thursdays at 11:00 and Saturdays at 11:30, and on the BBC World Service Listen to the programme Download the programme It might seem like a bit of a pantomime, but reception centres in Finland take these voluntary manners and culture classes extremely seriously. If men arriving from very different and conservative cultures are not immediately made aware that Finland has its own set of customs and rules which must be respected, then they will never integrate, warns Johanna. The men may groan when she tells them that Finnish men share the housework, but they no longer baulk when they see their taxi driver is a woman. Since the autumn, when Johanna first started giving these classes, female asylum seekers frequently approach her to complain that their husbands are not treating them in the Finnish way. The men are also versed in Finnish criminal law so they know exactly what to expect if they touch a woman inappropriately. And that's why these classes are backed by the interior ministry and the police. Last autumn three asylum seekers were convicted of rape in Finland, and at the new year there were a series of sexual assaults and harassments similar to those in Cologne and Stockholm. Victims reported that the perpetrators were of Middle Eastern appearance - something Helsinki's deputy chief of police, Ilkka Koskimaki decided to go public with. \"It's difficult to talk about,\" he admits as we drive in a patrol car through the icy streets of the city. \"But we have to tell the truth. Usually we would not reveal the ethnic background of a suspect, but these incidents, where groups of young foreign men,\" as he puts it, \"surround a girl in a public place and harass her have become a phenomenon.\" Image copyright Getty Images Image caption More than 32,000 migrants arrived in Finland in 2015 The police van pulls up at a downtown reception centre where Koskimaki's preventive policing team give similar classes to Johanna's. A jumble of migrant men smoking on the snowy steps in flip-flops, hastily scarper indoors, clearly alarmed by the police presence. A muscly Iraqi man in gym kit approaches me cautiously and asks me in a whisper why I feel the need to visit the centre with three police bodyguards. Please, he pleads, please don't think all asylum seekers are dangerous because of a few criminals. The lesson at Raasepoori reception centre is drawing to a close and the asylum seekers have been given optional homework to help them read up on Finland's sexual equality laws. As we leave the class, an Iraqi man in a colourful bomber jacket shakes my hand. \"It's great in Finland,\" he says \"But when I marry, my wife will be a housekeeper who will cook the food I like - and she certainly won't go to discos.\" How to listen to From Our Own Correspondent: BBC Radio 4: Thursdays at 11:00 and Saturdays at 11:30. Listen online or download the podcast. BBC World Service: At weekends - see World Service programme schedule or listen online. You can also listen to a longer radio report from Finland by Emma Jane Kirby which was broadcast on The World at One. Subscribe to the BBC News Magazine's email newsletter to get articles sent to your inbox.";
		int globalcount=0;
		String finalcountry="";
		Pattern re = Pattern.compile("[^.!?\\s][^.!?]*(?:[.!?](?!['\"]?\\s|$)[^.!?]*)*[.!?]?['\"]?(?=\\s|$)", Pattern.MULTILINE | Pattern.COMMENTS);
	    Matcher reMatcher = re.matcher(Intro);
	    while (reMatcher.find()) 
	    {
	    	sent.add(reMatcher.group());
	        
	    }
	    
	    for(int i=0;i<sent.size();i++)
	    {
	    	System.out.println(sent.get(i));
	    }

	}

}
