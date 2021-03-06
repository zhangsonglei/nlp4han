package com.lc.nlp4han.pos.character;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.lc.nlp4han.util.FeaturesTools;

/**
 * 根据配置文件配置的特征的信息，生成上下文的特征
 * @author 王馨苇
 *
 */
public class CharPOSContextGeneratorConf implements CharPOSContextGenerator{

	private boolean c_2Set;
    private boolean c_1Set;
    private boolean c0Set;
    private boolean c1Set;
    private boolean c2Set;
    private boolean c_2c_1Set;
    private boolean c_1c0Set;
    private boolean c0c1Set;
    private boolean c1c2Set;
    private boolean c_1c1Set;
    private boolean w0c0Set;
    private boolean PuSet;
    private boolean TSet;
    private boolean Pc_1w0Set;
    private boolean Pc_2w0Pc_1w0Set;
//    private boolean Bc_1w0Pc_1w0Set;
//    private boolean Bc_2w0Pc_2w0Bc_1w0Pc_1w0Set;
    
	/**
	 * 无参构造，加载feature配置文件
	 * @throws IOException
	 */
	public CharPOSContextGeneratorConf() throws IOException{
		Properties featureConf = new Properties();
        InputStream featureStream = CharPOSContextGeneratorConf.class.getClassLoader().getResourceAsStream("com/lc/nlp4han/pos/character/feature.properties");
        featureConf.load(featureStream);
        
        init(featureConf);
	}
	
	/**
	 * 有参数的构造函数
	 * @param properties 配置文件
	 */
	public CharPOSContextGeneratorConf(Properties properties){
        
        init(properties);
	}
	
	/**
	 * 根据配置文件初始化特征
	 * @param properties 配置文件
	 */
	private void init(Properties config) {
		c_2Set = (config.getProperty("feature.c_2", "true").equals("true"));
        c_1Set = (config.getProperty("feature.c_1", "true").equals("true"));
        c0Set = (config.getProperty("feature.c0", "true").equals("true"));
        c1Set = (config.getProperty("feature.c1", "true").equals("true"));
        c2Set = (config.getProperty("feature.c2", "true").equals("true"));

        c_2c_1Set = (config.getProperty("feature.c_2c_1", "true").equals("true"));
        c_1c0Set = (config.getProperty("feature.c_1c0", "true").equals("true"));
        c0c1Set = (config.getProperty("feature.c0c1", "true").equals("true"));
        c1c2Set = (config.getProperty("feature.c1c2", "true").equals("true"));

        c_1c1Set = (config.getProperty("feature.c_1c1", "true").equals("true"));
        
        w0c0Set = (config.getProperty("feature.w0c0", "true").equals("true"));
        
        PuSet = (config.getProperty("feature.Pu", "true").equals("true"));
        
        TSet = (config.getProperty("feature.T", "true").equals("true"));
        
        Pc_1w0Set = (config.getProperty("feature.Pc_1w0", "true").equals("true"));
        
        Pc_2w0Pc_1w0Set = (config.getProperty("feature.Pc_2w0Pc_1w0", "true").equals("true"));
        
//        Bc_1w0Pc_1w0Set = (config.getProperty("feature.Bc_1w0Pc_1w0", "true").equals("true"));
//        Bc_2w0Pc_2w0Bc_1w0Pc_1w0Set = (config.getProperty("feature.Bc_2w0Pc_2w0Bc_1w0Pc_1w0", "true").equals("true"));	
        
	}

	@Override
	public String toString() {
		return "WordSegPosContextGeneratorConf{" + "c_2Set=" + c_2Set + ", c_1Set=" + c_1Set + 
                ", c0Set=" + c0Set + ", c1Set=" + c1Set + ", c2Set=" + c2Set + 
                ", c_2c_1Set=" + c_2c_1Set + ", c_1c0Set=" + c_1c0Set + 
                ", c0c1Set=" + c0c1Set + ", c1c2Set=" + c1c2Set + 
                ", c_1c1Set=" + c_1c1Set + ",w0c0Set="+ w0c0Set +",PuSet="+ PuSet +",TSet="+ TSet +
                ",Pc_1w0Set="+ Pc_1w0Set +
                ",Pc_2w0Pc_1w0Set="+ Pc_2w0Pc_1w0Set +"";
	}

	/**
	 * 根据当前下标生成上下文
	 * @param i 当前字下标
	 * @param j 当前词的下标
	 * @param characters 字符序列
	 * @param tags 字符的标记序列
	 * @param words 词语序列
	 * @param poses 词语的标记序列
	 * @param ac 额外的信息
	 * @return 上下文信息
	 */
	private String[] getContext(int i, String[] characters, String[] tagsAndPoses, String[] words) {
		String c1, c2, c0, c_1, c_2;
        c1 = c2 = c0 = c_1 = c_2 = null;
        String TC_1, TC_2, TC0, TC1, TC2;
        TC_1 = TC_2 = TC0 = TC1 = TC2 = null;
        String w0,w_1,w_2,p_1,p_2;
        w0 = w_1 = w_2 = p_1 = p_2 = null;
        c0 = characters[i].split("_")[0];
        int record = -1;
		int len = 0;
		for (int j = 0; j < words.length; j++) {				
			len += words[j].length();
			if((i+1) <= len){
				record = j;
				break;
			}
		}
        w0 = words[record];
        if(record - 1 >= 0){
        	w_1 = words[record - 1];
        	if(record - 2 >= 0){
        		w_2 = words[record - 2];
        	}
        }
        TC0 = FeaturesTools.featureType(c0);
        if (characters.length > i + 1)
        {
            c1 = characters[i + 1].split("_")[0];
            TC1 = FeaturesTools.featureType(c1);

            if (characters.length > i + 2)
            {
                c2 = characters[i + 2].split("_")[0];
                TC2 = FeaturesTools.featureType(c2);
            }
        }

        if (i - 1 >= 0)
        {
            c_1 = characters[i - 1].split("_")[0];
            p_1 = tagsAndPoses[i - 1].split("_")[1];
            TC_1 = FeaturesTools.featureType(c_1);

            if (i - 2 >= 0)
            {
                c_2 = characters[i - 2].split("_")[0];
                p_2 = tagsAndPoses[i - 2].split("_")[1];
                TC_2 = FeaturesTools.featureType(c_2);
            }
        }

        List<String> features = new ArrayList<String>();
       //c_2
        if(c_2 != null){
        	if(c_2Set){
        		features.add("c_2="+c_2);
        	}
        }
        //c_1
        if(c_1 != null){
        	if(c_1Set){
        		features.add("c_1="+c_1);
        	}
        }
		//c0
        if(c0Set){
        	features.add("c0="+c0);
        }
        //c1
        if(c1 != null){
        	if(c1Set){
        		features.add("c1="+c1);
        	}
        }
        //c2
        if(c2 != null){
        	if(c2Set){
        		features.add("c2="+c2);
        	}
        }
        //c_2c_1
        if(c_2 != null && c_1 != null){
        	if(c_2c_1Set){
        		features.add("c_2c_1="+c_2+c_1);
        	}
        }
        //c_1c0
        if(c_1 != null){
        	if(c_1c0Set){
        		features.add("c_1c0="+c_1+c0);
        	}
        }
        //c0c1
        if(c1 != null){
        	if(c0c1Set){
        		features.add("c0c1="+c0+c1);
        	}
        }
        //c1c2
        if(c1 != null && c2 != null){
        	if(c1c2Set){
        		features.add("c1c2="+c1+c2);
        	}
        }
        //c_1c1
        if(c_1 != null && c1 != null){
        	if(c_1c1Set){
        		features.add("c_1c1="+c_1+c1);
        	}
        }
        
        //W0C0
        if(w0c0Set){
        	features.add("w0c0="+w0+c0);
        }
        //Pu
        if(PuSet){
        	if (FeaturesTools.isChinesePunctuation(FeaturesTools.strq2b(c0)))
                features.add("Pu=" + 1);
            else
                features.add("Pu=" + 0);
        }
        //T
        if(TC_2 != null && TC_1 != null && TC1 != null && TC2 != null){
        	if(TSet){
        		features.add("T="+TC_2+TC_1+TC0+TC1+TC2);
        	}
        }

        //pos(c_1w0)
        if(w_1 != null && (w_1.length() == 1)){
        	if(Pc_1w0Set){
            	features.add("Pc_1w0="+p_1);
            }
        	if(w_2 != null){
        		if(Pc_2w0Pc_1w0Set){
        			features.add("Pc_2w0Pc_1w0="+p_2+p_1);
        		}
        	}
        }
        //pos(c_2w0)pos(c_1w0)
        if(w_1 != null && w_1.length() > 1){
        	if(Pc_1w0Set){
            	features.add("Pc_1w0="+p_1);
            }
        	if(Pc_2w0Pc_1w0Set){
    			features.add("Pc_2w0Pc_1w0="+p_1+p_1);
    		}
        }
        
        String[] contexts = features.toArray(new String[features.size()]);
		return contexts;
	}

	/**
	 * 根据当前下标生成上下文
	 * @param i 当前字下标
	 * @param characters 字符序列
	 * @param tagsAndPoses 字符的标记序列
	 * @param ac 额外的信息
	 * @return 上下文信息
	 */
	public String[] getContext(int i, String[] characters, String[] tagsAndPoses, Object[] ac) {
		return getContext(i,  characters, tagsAndPoses, (String[])ac);
	}	
}
