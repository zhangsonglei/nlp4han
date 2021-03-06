package com.lc.nlp4han.ner.character;

import java.util.ArrayList;

import com.lc.nlp4han.ner.word.NERParseStrategy;
import com.lc.nlp4han.ner.word.NERWordOrCharacterSample;

/**
 * 为基于字的命名实体识别解析语料
 * @author 王馨苇
 *
 */
public class NERParseCharacter implements NERParseStrategy{

	/**
	 * 解析语料，基于字的
	 */
	public NERWordOrCharacterSample parse(String sentence) {

		//改进变成单个字的
		String[] wordsAndPoses = sentence.split("\\s+");
		
		ArrayList<String> words = new ArrayList<String>();
	    ArrayList<String> tags = new ArrayList<String>();
	    
	    for (int i = 0; i < wordsAndPoses.length; i++) {
	    	
	    	//改进变成单个字的
	    	String[] wordanspos = wordsAndPoses[i].split("/");
	    	String word = wordanspos[0];
	    	String tag = wordanspos[1];
	    	if(tag.equals("o")){
	    		for (int j = 0; j < word.length(); j++) {
					words.add(word.charAt(j)+"");
					tags.add("o");
				}
	    	}else if(tag.equals("nr")){
	    		if(word.length() == 1){
	    			words.add(word);
	    			tags.add("s_nr");
	    		}else if(word.length() == 2){
	    			words.add(word.charAt(0)+"");
	    			tags.add("b_nr");
	    			words.add(word.charAt(1)+"");
	    			tags.add("e_nr");
	    		}else if(word.length() > 2){
	    			for (int j = 0; j < word.length(); j++) {
						if(j == 0){
							words.add(word.charAt(0)+"");
							tags.add("b_nr");
						}else if(j == word.length()-1){
							words.add(word.charAt(j)+"");
							tags.add("e_nr");
						}else{
							words.add(word.charAt(j)+"");
							tags.add("m_nr");
						}
					}
	    		}
	    	}else if(tag.equals("nt")){
	    		if(word.length() == 1){
	    			words.add(word);
	    			tags.add("s_nt");
	    		}else if(word.length() == 2){
	    			words.add(word.charAt(0)+"");
	    			tags.add("b_nt");
	    			words.add(word.charAt(1)+"");
	    			tags.add("e_nt");
	    		}else if(word.length() > 2){
	    			for (int j = 0; j < word.length(); j++) {
						if(j == 0){
							words.add(word.charAt(0)+"");
							tags.add("b_nt");
						}else if(j == word.length()-1){
							words.add(word.charAt(j)+"");
							tags.add("e_nt");
						}else{
							words.add(word.charAt(j)+"");
							tags.add("m_nt");
						}
					}
	    		}
	    	}else if(tag.equals("ns")){
	    		if(word.length() == 1){
	    			words.add(word);
	    			tags.add("s_ns");
	    		}else if(word.length() == 2){
	    			words.add(word.charAt(0)+"");
	    			tags.add("b_ns");
	    			words.add(word.charAt(1)+"");
	    			tags.add("e_ns");
	    		}else if(word.length() > 2){
	    			for (int j = 0; j < word.length(); j++) {
						if(j == 0){
							words.add(word.charAt(0)+"");
							tags.add("b_ns");
						}else if(j == word.length()-1){
							words.add(word.charAt(j)+"");
							tags.add("e_ns");
						}else{
							words.add(word.charAt(j)+"");
							tags.add("m_ns");
						}
					}
	    		}
	    	}	
		}
		return new NERWordOrCharacterSample(words,tags);
	}
}
