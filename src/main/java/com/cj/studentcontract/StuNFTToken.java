package com.cj.studentcontract;

import io.neow3j.devpack.Runtime;
import io.neow3j.devpack.*;
import io.neow3j.devpack.annotations.*;
import io.neow3j.devpack.constants.FindOptions;
import io.neow3j.devpack.contracts.ContractManagement;
import io.neow3j.devpack.events.Event3Args;

@DisplayName("StuNFTToken")
@ManifestExtra(key = "author", value = "cj")
@SupportedStandards("NEP-11")
public class StuNFTToken {

    static final Hash160 contractOwner = StringLiteralHelper.addressToScriptHash("NiT2RDVAfARk1o1w5PfHfFFoT5updAWvjD");

    static final StorageContext ctx = Storage.getStorageContext();
    static final StorageMap contractMap = new StorageMap(ctx, 0);
    static final StorageMap registryMap = new StorageMap(ctx, 1);
    static final StorageMap ownerOfMap = new StorageMap(ctx, 2);
    static final StorageMap balanceMap = new StorageMap(ctx, 3);

    // Keys of key-value pairs in NFT properties
    static final String propName = "name";
    static final String propImage = "image";
    static final String propStuid = "stuid";
    static final String propReportTitle = "reporttitle";
    static final String propMathematicsFinalscore = "mathematicsfinalscore";
    static final String propMathematicsOveralllevel = "mathematicsoveralllevel";
    static final String propHistoryOveralllevel = "historyoveralllevel";
    static final String propHistoryFinalscore = "historyfinalscore";
    static final String propPhysicsOveralllevel = "physicsoveralllevel";
    static final String propPhysicsFinalscore = "physicsfinalscore";
    static final String propGeographyOveralllevel = "geographyoveralllevel";
    static final String propGeographyFinalscore = "geographyfinalscore";
    static final String propBiologyOveralllevel = "biologyoveralllevel";
    static final String propBiologyFinalscore = "biologyfinalscore";
    static final String propPoliticsOveralllevel = "politicsoveralllevel";
    static final String propPoliticsFinalscore = "politicsfinalscore";
    static final String propChemistryOveralllevel = "chemistryoveralllevel";
    static final String propChemistryFinalscore = "chemistryfinalscore";
    static final String propEnglishOveralllevel = "englishoveralllevel";
    static final String propEnglishFinalscore = "englishfinalscore";
    static final String propChineseOveralllevel = "chineseoveralllevel";
    static final String propChineseFinalscore = "chinesefinalscore";
    static final String propTeachersComments = "teacherscomments";


    static final StorageMap propertiesNameMap = new StorageMap(ctx, 4);
    static final StorageMap propertiesStuidMap = new StorageMap(ctx, 28);

    static final StorageMap propertiesReportTitleMap = new StorageMap(ctx, 27);
    static final StorageMap propertiesImageMap = new StorageMap(ctx, 5);
    static final StorageMap propertiesMathematicsFinalscoreMap = new StorageMap(ctx, 6);
    static final StorageMap propertiesMathematicsOveralllevelMap = new StorageMap(ctx, 7);
    static final StorageMap propertiesHistoryOveralllevelMap = new StorageMap(ctx, 8);
    static final StorageMap propertiesHistoryFinalscoreMap = new StorageMap(ctx, 9);
    static final StorageMap propertiesPhysicsOveralllevelMap = new StorageMap(ctx, 10);
    static final StorageMap propertiesPhysicsFinalscoreMap = new StorageMap(ctx, 11);
    static final StorageMap propertiesGeographyOveralllevelMap = new StorageMap(ctx, 12);
    static final StorageMap propertiesGeographyFinalscoreMap = new StorageMap(ctx, 13);
    static final StorageMap propertiesBiologyOveralllevelMap = new StorageMap(ctx, 14);
    static final StorageMap propertiesBiologyFinalscoreMap = new StorageMap(ctx, 15);
    static final StorageMap propertiesPoliticsOveralllevelMap = new StorageMap(ctx, 16);
    static final StorageMap propertiesPoliticsFinalscoreMap = new StorageMap(ctx, 17);
    static final StorageMap propertiesChemistryOveralllevelMap = new StorageMap(ctx, 18);
    static final StorageMap propertiesChemistryFinalscoreMap = new StorageMap(ctx, 19);
    static final StorageMap propertiesEnglishOveralllevelMap = new StorageMap(ctx, 20);
    static final StorageMap propertiesEnglishFinalscoreMap = new StorageMap(ctx, 21);
    static final StorageMap propertiesChineseOveralllevelMap = new StorageMap(ctx, 23);
    static final StorageMap propertiesChineseFinalscoreMap = new StorageMap(ctx, 24);
    static final StorageMap propertiesTeachersCommentsMap = new StorageMap(ctx, 26);

    static final byte[] totalSupplyKey = new byte[]{0x10};
    static final byte[] tokensOfKey = new byte[]{0x11};

    // NEP-11 Methods

    @Safe
    public static String symbol() {
        return "StuNFT";
    }

    @Safe
    public static int decimals() {
        return 0;
    }

    @Safe
    public static int totalSupply() {
        return contractMap.getInt(totalSupplyKey);
    }

    @Safe
    public static int balanceOf(Hash160 owner) {
        return balanceMap.getIntOrZero(owner.toByteArray());
    }

    @Safe
    public static Iterator<ByteString> tokensOf(Hash160 owner) {
        return (Iterator<ByteString>) Storage.find(
                ctx.asReadOnly(), createTokensOfPrefix(owner), FindOptions.RemovePrefix);
    }

    public static boolean transfer(Hash160 to, ByteString tokenId, Object[] data) {
        Hash160 owner = ownerOf(tokenId);
        assert 1==2 : "transfer is not allowed right now";
        onTransfer.fire(owner, to, tokenId);
        return false;
    }

    @Safe
    public static Hash160 ownerOf(ByteString tokenId) {
        ByteString owner = ownerOfMap.get(tokenId);
        if (owner == null) {
            return null;
        }
        return new Hash160(owner);
    }

    @Safe
    public static Iterator<Iterator.Struct<ByteString, ByteString>> tokens() {
        return (Iterator<Iterator.Struct<ByteString, ByteString>>) registryMap.find(FindOptions.RemovePrefix);
    }

    @Safe
    public static Map<String, String> properties(ByteString tokenId) {
        Map<String, String> p = new Map<>();
        ByteString tokenName = propertiesNameMap.get(tokenId);
        assert tokenName != null : "This token id does not exist.";

        p.put(propName, tokenName.toString());
        ByteString tokenImage = propertiesImageMap.get(tokenId);
        if (tokenImage != null) {
            p.put(propImage, tokenImage.toString());
        }

        ByteString reporttitle = propertiesReportTitleMap.get(tokenId);
        if (reporttitle != null) {
            p.put(propReportTitle, reporttitle.toString());
        }

        ByteString stuid = propertiesStuidMap.get(tokenId);
        if (stuid != null) {
            p.put(propStuid, stuid.toString());
        }

        ByteString mathematicsfinalscore = propertiesMathematicsFinalscoreMap.get(tokenId);
        if (mathematicsfinalscore != null) {
            p.put(propMathematicsFinalscore, mathematicsfinalscore.toString());
        }

        ByteString mathematicsoveralllevel = propertiesMathematicsOveralllevelMap.get(tokenId);
        if (mathematicsoveralllevel != null) {
            p.put(propMathematicsOveralllevel, mathematicsoveralllevel.toString());
        }

        ByteString Historyfinalscore = propertiesHistoryFinalscoreMap.get(tokenId);
        if (Historyfinalscore != null) {
            p.put(propHistoryFinalscore, Historyfinalscore.toString());
        }

        ByteString Historyoveralllevel = propertiesHistoryOveralllevelMap.get(tokenId);
        if (Historyoveralllevel != null) {
            p.put(propHistoryOveralllevel, Historyoveralllevel.toString());
        }

        ByteString Physicsfinalscore = propertiesPhysicsFinalscoreMap.get(tokenId);
        if (Physicsfinalscore != null) {
            p.put(propPhysicsFinalscore, Physicsfinalscore.toString());
        }

        ByteString Physicsoveralllevel = propertiesPhysicsOveralllevelMap.get(tokenId);
        if (Physicsoveralllevel != null) {
            p.put(propPhysicsOveralllevel, Physicsoveralllevel.toString());
        }
        ByteString Geographyfinalscore = propertiesGeographyFinalscoreMap.get(tokenId);
        if (Geographyfinalscore != null) {
            p.put(propGeographyFinalscore, Geographyfinalscore.toString());
        }

        ByteString Geographyoveralllevel = propertiesGeographyOveralllevelMap.get(tokenId);
        if (Geographyoveralllevel != null) {
            p.put(propGeographyOveralllevel, Geographyoveralllevel.toString());
        }

        ByteString Biologyfinalscore = propertiesBiologyFinalscoreMap.get(tokenId);
        if (Biologyfinalscore != null) {
            p.put(propBiologyFinalscore, Biologyfinalscore.toString());
        }

        ByteString Biologyoveralllevel = propertiesBiologyOveralllevelMap.get(tokenId);
        if (Biologyoveralllevel != null) {
            p.put(propBiologyOveralllevel, Biologyoveralllevel.toString());
        }

        ByteString Politicsfinalscore = propertiesPoliticsFinalscoreMap.get(tokenId);
        if (Politicsfinalscore != null) {
            p.put(propPoliticsFinalscore, Politicsfinalscore.toString());
        }

        ByteString Politicsoveralllevel = propertiesPoliticsOveralllevelMap.get(tokenId);
        if (Politicsoveralllevel != null) {
            p.put(propPoliticsOveralllevel, Politicsoveralllevel.toString());
        }
        ByteString Chemistryfinalscore = propertiesChemistryFinalscoreMap.get(tokenId);
        if (Chemistryfinalscore != null) {
            p.put(propChemistryFinalscore, Chemistryfinalscore.toString());
        }

        ByteString Chemistryoveralllevel = propertiesChemistryOveralllevelMap.get(tokenId);
        if (Chemistryoveralllevel != null) {
            p.put(propChemistryOveralllevel, Chemistryoveralllevel.toString());
        }
        ByteString Englishfinalscore = propertiesEnglishFinalscoreMap.get(tokenId);
        if (Englishfinalscore != null) {
            p.put(propEnglishFinalscore, Englishfinalscore.toString());
        }

        ByteString Englishoveralllevel = propertiesEnglishOveralllevelMap.get(tokenId);
        if (Englishoveralllevel != null) {
            p.put(propEnglishOveralllevel, Englishoveralllevel.toString());
        }



        ByteString Chinesefinalscore = propertiesChineseFinalscoreMap.get(tokenId);
        if (Chinesefinalscore != null) {
            p.put(propChineseFinalscore, Chinesefinalscore.toString());
        }

        ByteString Chineseoveralllevel = propertiesChineseOveralllevelMap.get(tokenId);
        if (Chineseoveralllevel != null) {
            p.put(propChineseOveralllevel, Chineseoveralllevel.toString());
        }




        ByteString teacherscomments = propertiesTeachersCommentsMap.get(tokenId);
        if (teacherscomments != null) {
            p.put(propTeachersComments, teacherscomments.toString());
        }

        return p;
    }

    // Events

    @DisplayName("Mint")
    private static Event3Args<Hash160, ByteString, Map<String, String>> onMint;

    @DisplayName("Transfer")
    static Event3Args<Hash160, Hash160, ByteString> onTransfer;

    // Deploy, Update, Destroy

    @OnDeployment
    public static void deploy(Object data, boolean update) {
        if (!update) {
            //throwIfSignerIsNotContractOwner();
            contractMap.put(totalSupplyKey, 0);
        }
    }

    public static void update(ByteString script, String manifest) {
        throwIfSignerIsNotContractOwner();
        ContractManagement.update(script, manifest);
    }

    public static void destroy() {
        throwIfSignerIsNotContractOwner();
        ContractManagement.destroy();
    }

    // cj Custom Methods

    @Safe
    public static Hash160 contractOwner() {
        return contractOwner;
    }

    //cj mint the nft  tokenId---userid_REPORTid
    public static void mint(Hash160 owner, ByteString tokenId, Map<String, String> properties) {
        assert Runtime.checkWitness(contractOwner) : "No authorization.";
        assert registryMap.get(tokenId) == null : "This token id already exists.";
        assert properties.containsKey(propName) : "The properties must contain a value for the key 'name'.";

        String tokenName = properties.get(propName);
        propertiesNameMap.put(tokenId, tokenName);

        if (properties.containsKey(propImage)) {
            String image = properties.get(propImage);
            propertiesImageMap.put(tokenId, image);
        }
        String reporttitle = "";
        if (properties.containsKey(propReportTitle)) {
            reporttitle = properties.get(propReportTitle);
            propertiesReportTitleMap.put(tokenId, reporttitle);
        }
        if (properties.containsKey(propStuid)) {
            String stuid = properties.get(propStuid);
            propertiesStuidMap.put(tokenId, stuid);
        }

        if (properties.containsKey(propMathematicsFinalscore)) {
            String mathematicsfinalscore = properties.get(propMathematicsFinalscore);
            propertiesMathematicsFinalscoreMap.put(tokenId, mathematicsfinalscore);
        }

        if (properties.containsKey(propMathematicsOveralllevel)) {
            String mathematicsoveralllevel = properties.get(propMathematicsOveralllevel);
            propertiesMathematicsOveralllevelMap.put(tokenId, mathematicsoveralllevel);
        }

        if (properties.containsKey(propHistoryOveralllevel)) {
            String Historyoveralllevel = properties.get(propHistoryOveralllevel);
            propertiesHistoryOveralllevelMap.put(tokenId, Historyoveralllevel);
        }


        if (properties.containsKey(propHistoryFinalscore)) {
            String Historyfinalscore = properties.get(propHistoryFinalscore);
            propertiesHistoryFinalscoreMap.put(tokenId, Historyfinalscore);
        }

        if (properties.containsKey(propPhysicsOveralllevel)) {
            String Physicsoveralllevel = properties.get(propPhysicsOveralllevel);
            propertiesPhysicsOveralllevelMap.put(tokenId, Physicsoveralllevel);
        }


        if (properties.containsKey(propPhysicsFinalscore)) {
            String Physicsfinalscore = properties.get(propPhysicsFinalscore);
            propertiesPhysicsFinalscoreMap.put(tokenId, Physicsfinalscore);
        }

        if (properties.containsKey(propGeographyOveralllevel)) {
            String Geographyoveralllevel = properties.get(propGeographyOveralllevel);
            propertiesGeographyOveralllevelMap.put(tokenId, Geographyoveralllevel);
        }


        if (properties.containsKey(propGeographyFinalscore)) {
            String Geographyfinalscore = properties.get(propGeographyFinalscore);
            propertiesGeographyFinalscoreMap.put(tokenId, Geographyfinalscore);
        }



        if (properties.containsKey(propBiologyOveralllevel)) {
            String Biologyoveralllevel = properties.get(propBiologyOveralllevel);
            propertiesBiologyOveralllevelMap.put(tokenId, Biologyoveralllevel);
        }


        if (properties.containsKey(propBiologyFinalscore)) {
            String Biologyfinalscore = properties.get(propBiologyFinalscore);
            propertiesBiologyFinalscoreMap.put(tokenId, Biologyfinalscore);
        }

        if (properties.containsKey(propPoliticsOveralllevel)) {
            String Politicsoveralllevel = properties.get(propPoliticsOveralllevel);
            propertiesPoliticsOveralllevelMap.put(tokenId, Politicsoveralllevel);
        }


        if (properties.containsKey(propPoliticsFinalscore)) {
            String Politicsfinalscore = properties.get(propPoliticsFinalscore);
            propertiesPoliticsFinalscoreMap.put(tokenId, Politicsfinalscore);
        }

        if (properties.containsKey(propChemistryOveralllevel)) {
            String Chemistryoveralllevel = properties.get(propChemistryOveralllevel);
            propertiesChemistryOveralllevelMap.put(tokenId, Chemistryoveralllevel);
        }

        if (properties.containsKey(propChemistryFinalscore)) {
            String Chemistryfinalscore = properties.get(propChemistryFinalscore);
            propertiesChemistryFinalscoreMap.put(tokenId, Chemistryfinalscore);
        }
        if (properties.containsKey(propEnglishOveralllevel)) {
            String Englishoveralllevel = properties.get(propEnglishOveralllevel);
            propertiesEnglishOveralllevelMap.put(tokenId, Englishoveralllevel);
        }


        if (properties.containsKey(propEnglishFinalscore)) {
            String Englishfinalscore = properties.get(propEnglishFinalscore);
            propertiesEnglishFinalscoreMap.put(tokenId, Englishfinalscore);
        }


        if (properties.containsKey(propChineseOveralllevel)) {
            String Chineseoveralllevel = properties.get(propChineseOveralllevel);
            propertiesChineseOveralllevelMap.put(tokenId, Chineseoveralllevel);
        }


        if (properties.containsKey(propChineseFinalscore)) {
            String Chinesefinalscore = properties.get(propChineseFinalscore);
            propertiesChineseFinalscoreMap.put(tokenId, Chinesefinalscore);
        }


        if (properties.containsKey(propTeachersComments)) {
            String teacherscomments = properties.get(propTeachersComments);
            propertiesTeachersCommentsMap.put(tokenId, teacherscomments);
        }

        registryMap.put(tokenId, tokenId);
        ownerOfMap.put(tokenId, owner.toByteArray());
        new StorageMap(ctx, createTokensOfPrefix(owner)).put(tokenId, new ByteString(reporttitle));

        increaseBalanceByOne(owner);
        incrementTotalSupplyByOne();
        onMint.fire(owner, tokenId, properties);
    }


    // Private Helper Methods

    private static void throwIfSignerIsNotContractOwner() {
        assert Runtime.checkWitness(contractOwner) : "No authorization.";
    }

    private static void throwIfSignerIsNotOwner(Hash160 owner) {
        assert Runtime.checkWitness(owner) : "No authorization.";
    }

    private static void increaseBalanceByOne(Hash160 owner) {
        balanceMap.put(owner.toByteArray(), balanceOf(owner) + 1);
    }

    private static void decreaseBalanceByOne(Hash160 owner) {
        balanceMap.put(owner.toByteArray(), balanceOf(owner) - 1);
    }

    private static void incrementTotalSupplyByOne() {
        int updatedTotalSupply = contractMap.getInt(totalSupplyKey) + 1;
        contractMap.put(totalSupplyKey, updatedTotalSupply);
    }

    private static void decrementTotalSupplyByOne() {
        int updatedTotalSupply = contractMap.getInt(totalSupplyKey) - 1;
        contractMap.put(totalSupplyKey, updatedTotalSupply);
    }

    private static byte[] createTokensOfPrefix(Hash160 owner) {
        return Helper.concat(tokensOfKey, owner.toByteArray());
    }


}
