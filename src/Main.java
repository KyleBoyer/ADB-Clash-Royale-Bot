import java.awt.Color;
import java.awt.Point;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.IOUtils;

import se.vidstige.jadb.JadbConnection;
import se.vidstige.jadb.JadbDevice;
import se.vidstige.jadb.JadbException;
import se.vidstige.jadb.managers.PackageManager;
import se.vidstige.jadb.managers.Package;

public class Main {
	
	private static List<String> okColors = Arrays.asList(new String[] { "4eafff", "68bbff", "6abcff", "67baff", "66b9ff", "65b9ff", "69bbff", "4caeff", "48acff", "4badff", "50b0ff", "4aadff", "0054a8", "4daeff", "4fb0ff", "4fafff", "32bcff", "096dd2", "8fb7cb", "61d0ff", "49adff", "a3ddff", "83d5ff", "218df9", "0053a7", "50b8ff", "46aaff", "103860", "004ea4", "2791fe", "3aa2ff", "218cf9", "49acff", "3aa1ff", "0050a0", "4eb5ff", "0053a6", "50b3ff", "50b7ff", "004fa3", "46abff", "49b8ff", "0058ae", "2790ff", "1e8bff", "1b8bfa", "1d87fe", "1b88fd", "1f89fa", "1f8afc" });
	private static List<String> openChestColors = Arrays.asList(new String[] { "ffe05d", "ffc846", "fec745", "fedf5c", "ffc946", "fedf5d", "fec845", "fec846", "fee05c", "ffe15d", "fee05d", "ffe05c", "ffdf5d", "fec946", "fec945", "ffca47", "ffc845", "fee15c", "ffca46", "feca46", "fee15d", "ffcb47", "fdc645", "fec746", "ffc746", "ffdf5c", "fecb46", "fece5e", "fed166", "f1d669", "fdc748", "f8da5f", "ecdc94", "fdc950", "fece5c", "bcab62", "fecc46", "c7b35f", "fecd47", "d9ca85", "e8d175", "fdc94c", "ffcd47", "fed673", "ffcc47", "fdc646", "feca47", "fdde5d", "fed36d", "fee5a3", "ffcc49", "ffbe3c", "ffbe3b", "ffcb48", "fecb48", "ffcb49", "ffcc48", "ffc240", "ffbf3d", "fdd98b", "febe3b", "ffc644", "ffc441", "ffc13f", "ffc341", "ffc947", "fdbd3c", "febd3b", "fecb49", "ffca48", "ffc13e", "ffbd3b", "ffc745", "ffc543", "f6d066", "fecc49", "ffcd4a", "fdc453", "e5bc64", "fccf55", "ffc744", "fdc75c", "aa812a", "b0862c", "b68a2d", "fdc045", "fcd889", "fec244", "d8b571", "f2cf6d", "fcc95b", "fdcd6b", "fdc658", "fdcd4b", "c4993d", "fad05d", "fec44a", "e7c367", "fdc24c", "fdc350", "fdbf42", "ffbf3c", "fec947", "ffc03e", "febe3c", "fdd689", "fdbd3e", "fdcd4f", "fdca65", "fdbd3b", "fdbe40", "fdbd3d", "fdd789", "fece69", "febf3d", "ffbd3c", "fcc860", "fec03d", "ffc03d", "fdcc68", "feca48", "fdcd65", "fdd98d", "fdd88a", "febd3c", "fdc148", "fec240", "fdca5c", "fec442", "ffc340", "fec140", "fec03e", "ffc442", "fbbd41", "fecc48", "fcd98d", "fcbc3b", "ffc140", "febf3c", "fcc148", "ffc544", "ffc542", "fec441", "fec643", "ffc241", "fec544", "aa822a", "fdc44a", "fcbd3e", "ffc145", "fcbd3f", "c59a3d", "fccd65", "b1872c", "fcbe40", "fdc860", "e7c466", "fccb4a", "fccc68", "f9d05d", "fdbc3c", "fec148", "fcc95c", "fcd789", "f2ce6c", "fec744", "b68b2d", "fbcc65", "fdbc3b", "facb64", "fcd88a", "fec644", "fcc24e", "fec13f", "fec03f", "f3d070", "fec543", "fdc54d", "e0c16f", "fcca65", "fcdfa1", "fac75a", "fcc456", "fcbd3d", "fcbc3d", "fdce69", "fdd688", "fccd4f", "fbdd9c", "fddf9f", "fccb67", "fcbc3c", "fecb47", "d7b570", "fdcb63", "fcd78a", "fcde9e", "fbcc50", "fdd88b", "fccc4b", "fbd059", "facf58", "fbcb4b", "fbcc4e", "fdd056", "fcd686", "fccf54", "f7d063", "fbcc4d", "fcd683", "fccd4e", "fcdd9b", "b28c3c", "fcd788", "fcdf9e", "fbd788", "dcb96d", "e5bb64", "dcb86d", "fcd98b", "fcd588", "d0b47d", "cfb47d", "d8b670", "fcd689", "fddd9c", "fcdc9d", "fbde9e", "9f7928", "b28d3c", "fcdd9c", "eecf77", "eece77", "fcd47d", "fdcd68", "fdd98c", "997426", "b78b2e", "a07a28", "fdd98a", "916e24", "987426", "916f24", "f8d063", "fbca68", "fccd6c", "fcbb3b", "fdd176", "fbbb3d", "fbbc3d", "fbd179", "fbbb3b", "fcbd3c", "fdc95f", "feca60", "fdc346", "fcc54f", "feca5f", "fbd074", "fdcc65", "fbbc3f", "fccd6d", "fcc24c", "fdc24d", "fcc24d", "fdcd6a", "fbc351", "fcc049", "fbc251", "fcc04a", "fec149", "fdbf45", "fcbf42", "fcbf44", "fcc350", "fdc149", "fccd6a", "fec345", "ffc343", "fac759", "fcc75c", "fdc14b", "fdc95c", "fdc657", "fac85a", "fcc75b", "fbca67", "fac758", "fbcb64", "fbcb63", "facf72", "fbd072", "fdcf72", "fecc64", "fbc85a", "fbc456", "fcd076", "fcce72", "fec34f", "fcc557", "fec242", "fdc960", "fcc75d", "fec553", "fbc862", "fbc861", "fccc67", "fbd076", "fdca5b", "fcd179", "fcc65a", "ffc94a", "ffd571", "ffe15c", "ffc945", "fecc56", "ffd048", "ffde5a", "ffe195", "ffce48", "ffe25d", "ffe25e", "fecc47", "ffe35e", "fece48", "ffd36d", "ffcf48", "ffd673", "fee25d", "ffdf5b", "ffd149", "ffce5c", "fecf48", "ffda56", "ffd148", "ffd955", "ffd652", "ffe05b", "ffe35c", "fee05b", "ffe15b", "fedf5b", "fee195", "fed14d", "ffd950", "ffdb57", "ffd24e", "fdde5c", "ffd34e", "ffdd5a", "ffd24a", "ffe5a3", "ffcd49", "ffd64f", "ffdd59", "ffd74f", "ffd750", "ffe05a", "fed852", "ffde57", "ffe057", "ffdd54", "fedd57", "fedb56", "fedc56", "ffd854", "feda53", "fedc57", "ffdc58", "ffd550", "ffdf59", "ffd551", "ffd54f", "ffdd58", "ffd450", "ffd44e", "fed249", "fecf47", "fece47", "fdcc56", "ffd44c", "fecf49", "fecf4a", "ffd049", "ffd04c", "fed04b", "ffe25b", "ffd84f", "ffe25c", "ffce47", "fedf5a", "ffda55", "fedd56", "fedf57", "fee25e", "ffdb56", "feda55", "ffdc56", "ffdf56", "ffd956", "ffdb55", "fede56", "ffdf58", "ffde59", "fee25b", "ffe159", "fedf59", "fee15a", "ffdf5a", "ffdc59", "ffe15a", "ffe059", "fee25c", "fedd59", "ffde58", "ffe45e", "ffe058", "fee35d", "fede58", "ffe35d", "fedc58", "fedb57", "fed54f", "ffd54c", "fece4b", "fecf4b", "fed24b", "ffd34b", "ffd24b", "ffce4a", "fed04a", "fed34c", "fed54c", "ffd44d", "ffd54d", "fed74e", "ffd34d", "fed24c", "fed64d", "fecf4c", "ffd14c", "fed54b", "fed34b", "ffcf47", "fecd48", "fed048", "ffcc46", "ffcb46", "fed571", "fcc646", "ffcd48", "ffcf49", "ffd04a", "fed14a", "fece49", "ffd44b", "ffd44a", "fecd49", "fed04d", "fed24d", "ffd753", "fed853", "fed953", "ffd653", "ffdb53", "ffd552", "fed752", "ffd953", "fedb54", "fedc54", "ffdc55", "ffdd55", "ffde55", "feda54", "ffda54", "fed753", "ffd754", "ffd954", "ffdc53", "ffda52", "ffd34f", "ffd44f", "fed650", "fed34f", "fed44e", "fed34d", "ffd54e", "fed64e", "ffd850", "feda51", "ffd651", "ffd751", "fed952", "ffd451", "ffdb52", "ffd851", "ffd951", "ffda51", "fed855" });
	private static List<String> unlockingChestColors = Arrays.asList(new String[] { "51d88a", "51d98b", "52d98b", "50d88a", "51d789", "50d789", "53df92", "51d98a", "52df92", "54e193", "51d889", "51de91", "52d88a", "52da8b", "50d889", "53e093", "50dc90", "4fdb8f", "52de91", "4dd98d", "51d78a", "50d688", "4cd88c", "50dd90", "2ab066", "48d589", "31b86d", "30b76c", "4bd88b", "2eb56a", "51d88b", "2db469", "2cb268", "4eda8e", "4ad78b", "52d98a", "54e194", "33ba6f", "49d589", "61ad80", "68b487", "7dd0a0", "2fb66b", "47d488", "2bb267", "55e294", "3bc276", "39c074", "37be72", "52df91", "3ac175", "29af65", "4ad68a", "50d98b", "46d286", "2eb66b", "2ab166", "34bb6f", "2cb368", "46d287", "2dad66", "4dda8d", "4dda8e", "50d384", "4fd182", "43cf84", "36bc71", "247f4a", "4cd98c", "34bc70", "47d388", "36bd72", "38bf73", "2db56a", "2eb469", "45d185", "45d286", "3ab06e", "31b76c", "33b96e", "48d488", "32b96e", "29ae65", "2bb268", "32ba6e", "37bf73", "49d68a", "52dc8e", "50d98a", "55e293", "50d586", "3cc377", "41cd82", "4bb378", "52d88b", "3ab16e", "35bc71", "2dae66", "4ecc7c", "2ab167", "2db368", "42ce83", "2bb167", "47d387", "51dd91", "58b57f", "31b96e", "29b065", "2ab267", "35bb70", "42cf83", "4ecb7c", "52de92", "32b86d", "4ecc7d", "53df91", "4ece7f", "50d587", "57b57f", "237f4a", "2cb168", "4cd98d", "33bb6f", "50d88b", "3ec579", "50dc8f", "4bb478", "44d085", "2bb266", "44d185", "44d084", "53e092", "2bb367", "46d388", "2eb46a", "3ecb80", "34bb70", "30b66b", "40cd82", "31b96d", "3fcb80", "3dc478", "53e193", "54e093", "50d283", "2fb76c", "2cb369", "2db369", "35bc70", "43d084", "51d485", "41c97c", "3dca7f", "2fb56a", "42ce82", "52da8c", "4fd283", "36be72", "38c074", "3bc176", "4ad78a", "50dd91", "51da8b", "4edb8e", "50de90", "48d588", "49d689", "4ecb7b", "50d78a", "4fcf80", "52d889", "35bd71", "3fc376", "53dd8f", "50d687", "4ecd7e", "58b47f", "3abf72", "32b96d", "4fdc8f", "4fdc90", "51de90", "50d284", "3cb16f", "34ba6f", "40cc82", "51da8c", "39c175", "53dc8f", "51df91", "42cf84", "50d485", "50d486", "4ab378", "3fc67a", "34b96f", "4fdb8e", "51d688", "4fd183", "54e092", "4bd78b", "45d186", "43cf83", "36bd71", "2bb166", "6bb98b", "37be73", "54dd8f", "4fd385", "4cd68a", "2db268", "54e192", "50d689", "4dd98c", "36be73", "46d387", "54e091", "37bd73", "35b96c", "37bd72", "38bb6f", "7fd3a2", "39c075", "67b486", "36ba6e", "36bb71", "36bc72", "38bd73", "37bc70", "38bf74", "36b76a", "37ba6e", "77b890", "35b66a", "82d8a5", "33ba6e", "34ba6e", "39be74", "81d8a6", "35ba70", "70ba8d", "2db468", "37b86c", "2cb066", "237c49", "237344", "4bac74", "29af64", "24804b", "299256", "29b166", "2ab065", "24824c", "56b37d", "2ead66", "4ab377", "4db378", "4cb478", "4fb57b", "57b47f", "55b57e", "41b373", "5cb581", "2fae67", "34b16b", "40b171", "29b066", "2bb066", "2fb66a", "31b66c", "2fb66c", "30b66c", "30b56b", "91d8ae", "32b66d", "34b86e", "31b86c", "33b76d", "30b86d", "72cc98", "6cc28f", "62b182", "62ac80", "6cac85", "2ab266", "64b686", "2eb369", "30b46b", "2fb46a", "2db46a", "2db569", "83d2a3", "3fc479", "4ad589", "4bd589", "4cd88b", "49d488", "49d387", "47d084", "48d286", "47d589", "4ed98d", "4eda8d", "4ed082", "51dc8f", "4dca7c", "50da8d", "50de91", "4fda8e", "50db90", "50db8e", "46d184", "45cc80", "41c578", "43cb7e", "44ce83", "41c376", "42c77c", "42ca7d", "43ce82", "43d085", "44d186", "42c378", "44cd80", "45d084", "46d185", "43c67a", "45cf83", "42c679", "44cc80", "51dd90", "51df92", "53e094", "51d486", "52d789", "53e194", "54e094", "52df93", "53de91", "53df93", "52d486", "51d384", "53d889", "54dc8e", "54e294", "54de8f", "53d98a", "53d88a", "51d284", "52dc8f", "52dd8f", "51d587", "4ece7e", "51d98c", "4eca7b", "4fce7f", "4fce80", "52dd90", "51d687", "52e092", "4fd485", "50d385", "52d78a", "4fd081", "50d484", "4fcc7d", "4ecd7d", "52e093", "40c477", "40c274", "3abb6e", "3bc073", "3cc679", "3bc074", "3cc478", "3dc97e", "3cc97e", "3cc176", "3dc97d", "3dc277", "3cc074", "3dc67a", "3ec97e", "3bbc6f", "3cc175", "3cc378", "3dc579", "3cc87c", "3cc578", "3ac377", "3bc67a", "3ac075", "38be71", "37b96c", "39c276", "39bf75", "38bc6f", "3abe71", "38b96c", "3ac275", "3bc377", "39ba6d", "3cc87d", "3cc77b", "3abe72", "3bc478", "3eca80", "3ec277", "40c77b", "40c87c", "40c579", "40c57a", "41ce83", "40c97c", "41cc80", "41ce82", "3ec073", "42cc80", "42cd81", "42c87d", "41c77a", "40c476", "3fc174", "41c67b", "41c679", "3dbf71", "3fc478", "3fc97e", "3fcb81", "3fcc81", "3ec67a", "3dc074", "3dc378", "3dc276", "3cbd70", "3ec379", "3fc77a", "40ca7f", "40cc81", "40c87b", "3fc87c", "3ec275", "3ec478", "3dbe71", "38bd71", "2eb76c", "2fb86d", "2db66b", "2bb46a", "2cb56a", "41cd81", "30b96e", "40cc80", "2bb369", "2fb86c", "32ba6f", "3fcb7f", "2bb56a", "2bb469", "2cb56b", "32bb6f", "57b780", "35be72", "41ce81", "27804d", "31ba6f", "38c175", "34bd71", "3ac67a", "36bf73", "33bc70", "3eca7e", "40cd80", "43d083", "3fcc7f", "37c074", "3fcb7e", "2eb76b", "2db56b", "31bb6f", "32bb70", "42cf82", "33bd71", "44d184", "2eb66c", "2db66c", "30ba6e", "35bd72", "6eb78c", "31ba6e", "5bb782", "2cb46a", "30b96d", "3dca7d", "30b86e", "39c579", "33b36d", "6eb68c", "2cb66b", "2cb469", "207d48", "3bc578", "42ce81", "2db169", "46b678", "27814e", "38c478", "32bc70", "43cf82", "36c073", "46b476", "33bb70", "37c377", "37c174", "2cb66a", "207d47", "3bc87c", "44d083", "2fb76d", "2fb96d", "35bf73", "2bb368", "2ab168", "33bc71", "38c275", "34be71", "3ac57a", "39c376", "2ab067", "2db069", "3bc77b", "37c277", "46b577", "6eb68b", "45d184", "33b26c", "3cb371", "3cb270", "3ecb7e", "3cc77c", "4fb67c", "2db76b", "38c378", "33b16b", "57b881", "2daf68", "5bb681", "35be73", "3cc97c", "2bb569", "37c378", "3dc87d", "40cc7f", "40cb7f", "207e49", "34bc71", "3ac67b", "3dca7e", "44d183", "3ac77a", "39c377", "2db269", "2aaf66", "35bf74", "3ac579", "45d285", "2fb76b", "3cb472", "3eca7d", "4fb87d", "2eb66a", "2db66a", "33be73", "3fca7e", "34be72", "36c276", "3cb572", "30b86c", "5bb783", "34c075", "3bc77c", "4fb77d", "3bc77a", "3dc97c", "36c075", "3ac477", "40cd81", "35c075", "34bf74", "34be73", "2cb569", "3ac679", "39c479", "38c075", "3bc87b", "3fcc80", "37c276", "46b677", "5bb883", "36c277", "32bc71", "35c175", "34c074", "30ba6f", "2eb76d", "2eaf62", "36c175", "36c176", "38c578", "33b36c", "39c578", "39c679", "37c176", "2ab268", "3cb471", "39c478", "4fb77c", "3dc77a", "3ec77a", "3cc87b", "57b981", "41cd80", "35c176", "98eebc", "5bb781", "57b880", "33bd72", "3dc679", "38c377", "3fc87b", "3ecb7f", "35c276", "93e9b8", "2fb96e", "39c67a", "31bb70", "3ac77b", "83dba8", "35bf72", "2bb468", "36c074", "2db76c", "9be9bc", "83dca9", "39c176", "3ec97c", "45d284", "9fedbf", "a0eec0", "37c478", "97edbb", "32bd72", "5bb984", "38c477", "3ec87b", "39c378", "38c176", "3bc87d", "3bc47a", "a3f1c3", "50ca88", "44d082", "32be73", "84dda9", "31bc70", "31bc71", "3ec97d", "4fd089", "36c376", "44b576", "31b164", "217f49", "3abd70", "39c57a", "3cc97d", "23824c", "3cc579", "3dc77c", "a2f0c2", "27824e", "3bc477", "34bf73", "34c176", "3cc67b", "42cd80", "a5f4c5", "37bf74", "a4f3c4", "82daa7", "33be72", "207e48", "207f49", "33bf74", "38c579", "9ceabd", "3dbf72", "3fc97d", "42cb7f", "5cd693", "3cba6c", "51d38d", "2cb269", "9af1bf", "54d790", "36bf74", "a3f2c4", "38c479", "2fad5f", "3bc179", "2ab368", "3cbc6e", "3dc072", "30b467", "33b163", "36c275", "3ec174", "39c278", "3ec47c", "99e6b8", "36c377", "3dbb6d", "3dbd6f", "35c074", "99e5b7", "34c175", "4bd188", "34bd72", "35c277", "3fc173", "46c782", "3cc27a", "9decbe", "3ebc6e", "3fc274", "33c075", "9ae8bb", "2cb76b", "94eab9", "3ec173", "3fc577", "33bf73", "2db266", "2bb66a", "5cd794", "52d48e", "46c681", "37c479", "9cf3c1", "34b669", "34b76a", "9debbd", "54ce8b", "49cf86", "31b467", "48c984", "95ebb9", "30b063", "33b265", "3ec77b", "32b86c", "34b568", "5eda96", "3bbb6d", "2cab5e", "2faf62", "2dae62", "2eb367", "3dc87c", "39c073", "3ac478", "6cb78b", "2eb86d", "37b567", "a1efc1", "41c779", "4dc685", "31bc72", "39c275", "30b96f", "43c980", "3ec77c", "3ac376", "38bb6d", "32bc72", "3fca7d", "9df3c1", "39c277", "91e7b6", "3cc67c", "3bc174", "2fb86e", "39b96b", "3ec87d", "41c97f", "59d491", "99efbd", "4fb67b", "46cd83", "39b86a", "3abd6f", "47d287", "3bc67b", "30bb6f", "3bb572", "55d890", "3bc57a", "3ac277", "40c87e", "45ca82", "83dca8", "3abe77", "92e8b7", "50d18b", "50d28c", "37c073", "48cf85", "38c174", "5bd592", "31bd72", "42c77f", "9af0be", "36b96c", "3dc375", "91e6b5", "32be72", "39c379", "3bba6c", "3cbf71", "3fbe70", "38c276", "56d08d", "35b365", "33b26b", "55b77f", "3cbe70", "36b365", "37b86b", "2ead5f", "2fb367", "4ccc86", "3fc77d", "2dad60", "2eb76a", "2dac5e", "30b469", "2fac61", "35b26a", "2eae61", "3fbb76", "2fb468", "39bd74", "72d09a", "81d8a5", "2eb366", "2cab5f", "2eb86c", "22613b", "2cad60", "91e6b6", "90e4b4", "42c17c", "37ba70", "22824b", "42c27d", "4bc280", "4ec885", "38bc73", "82dba8", "2eaf61", "30b066", "2eb368", "2eb267", "36b76e", "4cc382", "40be79", "2cac60", "83daa7", "44ac71", "2bac5f", "2ba95d", "248b52", "6eba8e", "32b86f", "4dc683", "3abe75", "2a975a", "90e5b4", "41c07b", "43c37d", "4ec785", "78c99a", "2daf63", "8eb99d", "80d8a6", "9be9bb", "2baa5d", "3dbb75", "34b46e", "32b26c", "35b56f", "3ab26f", "56b881", "97e5b7", "3ca66b", "34b36e", "54b67f", "40b876", "46ba79", "3ab671", "74bb91", "37b36f", "57b882", "289b5b", "43c37e", "2faf69", "44af73", "2db068", "4abd7d", "8ee0b0", "2eb269", "4cbd7d", "8fe0b1", "49bd7c", "40b976", "31b26b", "39b671", "4dbc7e", "48b97a", "7cd7a3", "4eb87c", "68b98a", "37ad6b", "4fbb7e", "49ba7b", "4ebc7f", "46b576", "45b677", "2ca461", "76d39d", "53bb80", "4fb97e", "49b87a", "4cb87c", "43b776", "3fb574", "3cb370", "35a567", "2ba15f", "76d29d", "89ddac", "8ce0af", "52b87e", "56bb82", "3ab471", "4db67b", "4ab579", "4fb97d", "52b77e", "51b97e", "44bc79", "3bb973", "8fe3b1", "31b76d", "35b36a", "4abc79", "3db46d", "30b267", "34af65", "3db36d", "49b975", "49b875", "8eddac", "3eb871", "34b76f", "8fe0af", "2fb56b", "2db669", "1c5f36", "3eb870", "4ac07d", "33ad64", "83d9a7", "2daa5f", "27814d", "90e4b2", "9ae7b9", "23864e", "23814b", "249255", "249556", "69c892", "309e60", "99e4b6", "6fce98", "8edfaf", "99e4b5", "32b66e", "4ac07f", "40bd77", "31b66d", "38ba72", "7abc94", "39b972", "7bd5a1", "33b56e", "8fe1b2", "42bd79", "4cc080", "98e1b3", "59b882", "49bc79", "8ddbaa", "329f62", "279959", "35b970", "31a062", "98e3b4", "46aa71", "4bc17f", "8fdeae", "3ebd77", "36b971", "54b27c", "30b76d", "2db062", "3bc378", "38b567", "a1f0c2", "58d38f", "39bc6e", "3ac072", "4ecf88", "38b769", "39bb6e", "a1efc2", "3ac378", "38ba6d", "58d28f", "4ece88", "3abf73", "3dc67c", "38b86a", "38b768", "3bc579", "3cc77a", "39b96a", "3cc67a", "a2f1c3", "47ce84", "5ad491", "99efbe", "39b86b", "3abc6f", "59d390", "99eebd", "39bd6f", "46cb83", "42ca80", "3ec77d", "37b769", "38bb6e", "36b86a", "35b567", "37be70", "3dc47b", "37b96d", "43c880", "36b567", "42c87f", "4bcc85", "35b467", "37bd71", "3dc57c", "56d08c", "97edba", "36b568", "a0efc0", "3ac578", "37b768", "4dce87", "57d28f", "4dce88", "40c97f", "44ca82", "57d28e", "39c477", "38bf72", "4ccd86", "56d18d", "37bb6d", "36b667", "37ba6c", "36b466", "43cd81", "9cf2c0", "a4f3c5", "41ca7d", "5dd995", "53d58f", "46d086", "3ebb6d", "40c577", "4bd087", "53d48e", "5dd794", "46d085", "42cd82", "41cc7f", "41ca7e", "40c678", "41c374", "42c779", "43cc7e", "a5f4c6", "5fda97", "43cc7f", "4cd48a", "43cf81", "43d082", "3fbd6e", "45d085", "3ebd6f", "4cd389", "5fda96", "47d186", "4cd489", "9cf2bf", "40ca7d", "41cb7f", "47cd83", "5bd491", "3dc275", "3bba6b", "43cd82", "3ec679", "9aefbe", "3aba6c", "3bbe70", "3cc275", "3aba6b", "3bbf71", "3dc77b", "3bbb6c", "3cc073", "4ad087", "9bf1bf", "3ec476", "45cf85", "40ca7e", "43cd83", "9bf2bf", "44ce84", "48ce85", "3cbe71", "3ec87c", "42cc82", "3fc97c", "3fc77b", "3ec477", "4bcb85", "56cf8c", "30b062", "36be74", "30b164", "30ad60", "30ad5f", "38c177", "2fb062", "2faf61", "30b366", "9cebbd", "45c681", "32bd71", "3bc27a", "9debbe", "50cb89", "33b96c", "37c076", "51cb89", "32b568", "31b063", "32b366", "31ae61", "94e9b8", "33c074", "32b96c", "38c178", "3cc37b", "31b568", "30b163", "31b366", "30ae61", "31b86b", "37c075", "2dad5f", "2fb467", "2fba6e", "9ceabc", "44c57f", "4fc886", "92e8b6", "30bc70", "34bd73", "2fb569", "2eae62", "2dac5f", "9beabc", "91e8b6", "43c57e", "4ec886", "3ac077", "30b66a", "31be72", "39c077", "44c680", "2fb063", "93e8b7", "45c680", "50c987", "4fc987", "31bb71", "3ac078", "44c580", "4fc887", "36bf75", "30b367", "2faf63", "2eac5f", "51cc8a", "46c883", "38c278", "3cc57b", "40c67e", "38c277", "37c175", "34b366", "35b76a", "36bc6f", "4aca85", "55ce8b", "35bc6f", "36c378", "37c278", "33b366", "33b264", "96ebba", "34b264", "35b669", "35b96b", "36bd70", "37c275", "34b466", "34b364", "97ecba", "36bf72", "35b769", "37c477", "3ac479", "9feebf", "96ecba", "55cf8b", "40c67d", "4acb85", "3ac47a", "3fc67e", "32b062", "32b164", "33b669", "33b467", "42c77a", "52cd8a", "94ebb9", "9eedbe", "47c883", "32b669", "3dc47c", "31af61", "32b467", "52cc8a", "9eecbe", "47c983", "35c073", "3fc47d", "35bb6f", "37c177", "3ac279", "35bb6e", "48ca85", "95ebba", "3fc57d", "53cd8b", "32b264", "33b769", "34bb6e", "32b162", "33b466", "35c174", "34bc6f", "2fb368", "57e292", "56e191", "55e395", "56e090", "58e696", "53de90", "58e697", "58e797", "55df8f", "56e496", "59e797", "54de8e", "59e898", "54dd8e", "55e090", "55de8f", "56e495", "56e395", "55df90", "57e191", "55e394", "59e798", "57e393", "54e293", "56e091", "56e596", "57e291", "52de90", "53dd90", "57e192", "58e393", "59e999", "54df8f", "59e494", "56e292", "54e394", "54dd8d", "58e898", "5ae595", "52de8f", "58e292", "57e597", "5ce797", "9aebbc", "57e494", "57e696", "57e596", "53de8f", "55dd8f", "59e393", "56e192", "5be696", "57e293", "56df90", "59e293", "58e596", "55e295", "58e092", "55de8e", "57e697", "54df90", "58e798", "a8faca", "55e495", "58e192", "59e697", "8ef6bb", "57df91", "56e394", "59e897", "5be595", "56e190", "51dc8e", "9aeabc", "54d684", "a9faca", "5ae394", "57d08a", "53dd8e", "5de898", "56e291", "53e294", "56df8f", "53db8d", "5ae494", "59e799", "59e998", "56de90", "56e396", "54dc8d", "58e494", "55e091", "55e194", "53e192", "58e597", "52db8d", "54df8e", "67eba1", "5ae495", "57e091", "54d785", "57e394", "55e191", "57e496", "8ff7bb", "58e698", "54df91", "59e698", "53db8c", "55e08f", "57e497", "5be695", "55e190", "57e395", "58e598", "5ae594", "5ae999", "57e392", "59db90", "59e394", "59e597", "59e595", "68eca2", "55e092", "53dd8d", "5ce898", "55d988", "4fcd7e", "5ae696", "51dd8e", "58e897", "5ae899", "58e293", "56e595", "56e294", "57e090", "58e695", "57e797", "56e193", "52d585", "52d686", "5be796", "53d786", "54db8c", "53e293", "55da89", "57e595", "53d787", "53d686", "59e292", "67eaa1", "56db8a", "55db89", "55e193", "56e092", "5be99a", "54e395", "5ae898", "56de8f", "51db8d", "4fcd7d", "54df92", "55dc8a", "57e190", "56e597", "5deb9b", "5ceb9b", "5ae493", "55e192", "5be495", "57e695", "58e899", "52db8e", "5de99b", "5ce695", "5ce696", "5de999", "58e191", "56e08f", "5bea9a", "53d887", "56e392", "5de899", "5dec9c", "5eec9c", "5cea9a", "50d183", "5ae799", "51da8d", "55e093", "55dd8e", "55e396", "52e091", "5de998", "53db8b", "5be797", "53dc8d", "54de90", "55e496", "5be999", "5de99a", "5be494", "5de897", "51dd8f", "52dd8e", "59e495", "5ce899", "53de8e", "5ae695", "58db90", "67eca2", "59e796", "58e294", "53dc8c", "58e091", "58e496", "58e395", "57e294", "58e291", "5be596", "51d283", "5ce596", "59e493", "5eed9d", "56e194", "54dd8c", "59e699", "55dc8e", "53d98b", "5adc91", "56e295", "59e392", "56d989", "aafacb", "55df91", "58e497", "55dc8d", "56df91", "58e396", "54d586", "5ae89a", "5ae393", "59e496", "57e193", "57df90", "55de90", "5ce897", "8ef7bb", "4fd082", "57d18b", "58e796", "56dc8a", "53e091", "50d182", "55db8a", "54e295", "54d988", "54d987", "59ea9a", "59e899", "56e293", "58e595", "55dc8c", "59de93", "55da88", "55dc8b", "59de94", "56e494", "53da89", "52df90", "58e998", "58e999", "57d38c", "54dc8c", "52d685", "53dc8e", "52d98c", "5ce799", "54de8d", "57d28c", "5ee394", "52dd91", "59e192", "57de8f", "5ce595", "5ae79a", "57da8a", "53da8a", "56da89", "56e393", "54da8a", "5deb9c", "62ed9e", "6cefa6", "53d988", "5adf8e", "55db88", "54d786", "5ce999", "5be392", "59dc8a", "58d18b", "5de89b", "6befa4", "61eb9d", "93f8be", "acfacc", "5cd58f", "5dea9b", "5adf8d", "5be594", "5ceb9a", "57d48e", "5ee095", "57de90", "59dd93", "57d38d", "94f8bf", "56dc8e", "54d383", "57e092", "57e093", "56da8c", "53d98c", "58df91", "4fd384", "54db8a", "5ee998", "5ee999", "5eeb9a", "5de797", "6ce29d", "54d989", "5ce796", "63e398", "92d4b1", "5eeb9b", "5ce391", "63ee9f", "6df1a6", "95f9c0", "adface", "5fec9d", "5fed9d", "5bdd8a", "5ce18f", "5de796", "5eed9c", "5ce798", "55d484", "57dc8c", "56d787", "57dd8e", "59e395", "59e294", "58e193", "5dd690", "5fe195", "58db89", "58dd92", "59e497", "59e598", "59e090", "58db8a", "58db8b", "56da8a", "5ae798", "5be89a", "5be799", "71db9e", "61e29a", "59ea99", "acfacd", "53df90", "5fea9b", "58e997", "69eda3", "91f8bd", "5ad38d", "59dc92", "58dc8b", "53d685", "50cf80", "58df8d", "56d987", "5cde93", "56db89", "58db91", "4fcc7e", "58d488", "56dc8d", "50d082", "52dc90", "53d788", "52d181", "52d384", "54da8c", "59e594", "90f7bd", "5ee99a", "52d787", "59d28c", "5bde92", "54da8b", "54d784", "57dc8b", "56d886", "57db8a", "5de799", "54db8d", "54dd90", "54d787", "54d889", "52da8d", "54d685", "53d585", "53dd91", "51d080", "55dd8d", "54d687", "55d888", "55d989", "56d988", "57de8e", "55e292", "5de89a", "58e190", "53d989", "52d586", "53d282", "57d488", "54c57d", "5ce99a", "58da8e", "56dd8c", "58e594", "58e493", "52d584", "5ce89b", "56cf89", "55ca82", "55d785", "53d888", "55e596", "55d98a", "68eba1", "5ae796", "55c882", "58df8e", "57da88", "58dd8c", "56ce83", "53da8c", "67eba2", "55df8e", "55e494", "58d18c", "5ce89a", "5bd48e", "5de094", "56dd8f", "54e08f", "abfacc", "92f8be", "8ff7bc", "59e696", "60eb9c", "6aeda4", "5ae08f", "6cf4a6", "6bf4a5", "6df7a9", "6df8a9", "6cf5a6", "6cf7a9", "6cf7a8", "6cf8a9", "4cd78b", "6bf3a4", "6cf4a5", "48d387", "62ea9c", "4bd68a", "60e89a", "69eb9b", "6cf3a6", "6af2a3", "69f1a2", "5fe799", "52b57d", "429e69", "42b274", "2aae65", "378c5c", "72be92", "3fc87d", "44cf83", "6cf8a8", "6df7a8", "47d285", "40c97e", "50db8f", "47d286", "41cb80", "5ee698", "5ce496", "63eb9e", "65ed9f", "69f0a1", "67efa0", "5ae395", "32af6a", "54de91", "6bf4a6", "66ee9f", "6bf5a6", "30af62", "2fb064", "30b064", "32af6b", "82d8a7", "83d9a8", "82d9a7", "32b66a", "31ae62", "2ead60", "2eb56b", "36c076", "32b468", "4dd88c", "5be497", "5ce597", "5ae295", "59dd8e", "55d485", "59e193", "5ee598", "63eb9d", "69f0a2", "6bf4a4", "67efa1", "66eea0", "65ed9e", "52d587", "54d585", "53d384", "6bf3a6", "6bf5a5", "49d588", "4dd88b", "4ac381", "45d083", "40c97d", "50dc8e", "69ed9d", "6aee9f", "6bf2a3", "6bf1a2", "6cf5a7", "6af0a0", "6bf3a5", "6cf6a8", "6cf8aa", "6cf6a7", "6aed9e", "6af1a1" } );
	private static List<String> backgroundChests = Arrays.asList(new String[] { "235b86", "1f517b", "1f517c", "1e4f7a", "235c87", "1f507a", "1d4d78", "20547e", "235b85", "20547f", "1f527d", "1f537e", "225985", "205783", "235b87", "1f517a", "215680", "1f527c", "1f5682", "225781", "1f547e", "225a85", "235a85", "1e5480", "1e4e79", "1e5580", "1d4d77", "194975", "1e5581", "1c527e", "1f4f7a", "1d4c77", "1d537f", "1d5480", "20557f", "1e547f", "215984", "194974", "1f5581", "215580", "225881", "215883", "1c537e", "225a86", "1d5682", "1f507b", "1e5784", "1b517d", "1f5582", "20537e", "235c88", "1d5683", "1d547f", "1e4e7a", "235d88", "1f537d", "225884", "215681", "1e507a", "1b517e", "1f517d", "245c87", "1e5783", "205884", "205683", "205580", "225b86", "1f5683", "1a4a76", "215985", "215781", "205682", "215a86", "21557f", "1a4a75", "1e5582", "1d537e", "184874", "225b87", "1d4b76", "1d5582", "1a4975", "1d4e79", "1c527d", "215884", "1d4f7a", "205883", "1c5481", "1f5681", "205784", "1e537f", "184872", "235c86", "215986", "205984", "1b507d", "235881", "1d4f7b", "1e5481", "184974", "1d5684", "1c537f", "194973", "245d88", "205681", "1a507c", "225984", "205782", "1d527e", "1d5380", "1f5580", "1d5481", "184873", "1f5782", "215783", "20537d", "174773", "164672", "235882", "1f5481", "225885", "1f5883", "1a507d", "205882", "1d527f", "1e4e78", "1c517e", "1f527e", "164671", "215885", "1b4a76", "184875", "1a4d78", "1b527d", "21547f", "1b527e", "184975", "235b88", "225b85", "164772", "1c4f7b", "1d446c", "225b88", "215a85", "1b507c", "1e5785", "1c537d", "215784", "1a4f7b", "1b4b76", "1f4c74", "1d5482", "1e4a73", "1d4e78", "1e4f7b", "205985", "1a4976", "1f4a73", "1d507c", "1a4f7c", "174772", "1f5885", "174873", "1a4b76", "21517a", "194874", "194976", "1f4f79", "1e5682", "164773", "245d87", "1f5785", "1f5784", "18365e", "1e507c", "1e5683", "1f547f", "20557e", "1e537e", "1e547e", "1e4f79", "1b4a75", "174672", "194873", "194a75", "1f5884", "1c517c", "1c517d", "235985", "235a86", "164571", "235d87", "225680", "1d517d", "21567f", "225a87", "1d507b", "1e507b", "1f5680", "1f5480", "1b4e79", "1d527d", "1d507a", "194875", "205581", "1e5684", "1a4d79", "184772", "1c527f", "235e89", "21547e", "1d547e", "215a84", "1d4a75", "1b517c", "1b5380", "1f4b73", "1e456c", "1f4e78", "22557e", "19416b", "1f5783", "1b4872", "1e517a", "194570", "164673", "174673", "193f68", "194c78", "1d4d79", "225681", "22547e", "1e446c", "184973", "194069", "194a76", "205582", "1c507c", "225780", "1c4d79", "1b4f7b", "1c5482", "1d4e7a", "1a406a", "1c5380", "1a4e7b", "225a84", "1e517d", "1e4770", "1b527f", "1a426c", "1a4069", "1f507c", "205983", "1c4b77", "1b507b", "1c4e79", "164471", "1f5882", "1d4f79", "1e5380", "236693", "1c4c78", "1a4a74", "1c4973", "15365e", "184774", "1c4e7a", "1f4b74", "184069", "17426d", "19436e", "20517c", "194c79", "205781", "1a4a77", "174874", "1b4c78", "1a4b77", "1c4e7b", "20527d", "154672", "1d4770", "245f89", "1b4e7a", "205684", "225986", "1b4d78", "1b4f7c", "154471", "1a517d", "1a4771", "245e89", "194b75", "1e5782", "154571", "1e436a", "1e5482", "154572", "1e466d", "1a4772", "1e5681", "1b436b", "153b65", "1b446d", "205583", "235781", "205986", "184773", "1c4974", "19426d", "1c4a74", "1f5583", "174774", "1b4c77", "205079", "183d67", "1f466d", "184977", "15355e", "153760", "1a517c", "235d89", "1c5480", "1e4870", "205480", "163f69", "1b4169", "1a416b", "183a63", "1c4f7c", "1b436c", "173c66", "194f7c", "1b507e", "164570", "215682", "1b4b77", "1b4975", "173c65", "1d5280", "15345d", "1d4369", "18426d", "20507a", "1d456c", "19406b", "1c4169", "184976", "1d466f", "183962", "21537e", "1b4670", "1b4e7b", "18436d", "1a507b", "1a4873", "1b4b78", "1a517e", "173a63", "1a527f", "1d4f7c", "194c77", "1a4871", "194d7b", "1b4d7a", "1d4e7b", "19436f", "1b4b75", "18406b", "1b4a77", "1d517c", "1a4c78", "1c4f7a", "1c4f79", "183c66", "18446f", "184570", "1c507b", "1b4d79", "173b63", "1f5684", "184470", "19436d", "184670", "1a4671", "1a4b78", "194670", "1a4974", "1a4c79", "1e4d77", "194b76", "1c466e", "1b426a", "1f4d77", "1c4a73", "183b64", "18355d", "1d4a74", "1a426b", "1c456d", "1d4670", "1b537f", "193c65", "1b5481", "205a85", "1b446e", "174a76", "1b456f", "183861", "193d66", "236390", "1c4772", "18375e", "1e4d78", "143660", "18375f", "1c436c", "1b4873", "15335d", "22567f", "164572", "20517a", "163a64", "245c88", "245c86", "235e88", "245f8a", "194e7c", "204d75", "1f456c", "154470", "1d426a", "21537d", "18426c", "1b5480", "15355d", "1e4871", "1a416a", "1c426a", "1f4f78", "1f4a72", "1d4269", "1d436a", "1f4971", "20527c", "143b66", "1c4671", "173d66", "15365f", "1b4772", "193f69", "1a436e", "235a87", "17406a", "1a4c76", "184a76", "163f68", "193b64", "1c4a75", "1e527d", "173d67", "1f5987", "17426e", "163f6b", "194d79", "1b456e", "1a456f", "1a3e68", "19446e", "1c4a76", "1d4b77", "153c67", "164470", "184b78", "163d68", "173b65", "194d7a", "1c5381", "174571", "184a77", "1b4976", "174670", "17406b", "194a77", "16355e", "173f69", "194c76", "174470", "163964", "17406c", "143863", "153964", "14335d", "16406b", "1a5280", "1b517f", "19507c", "163d69", "194b78", "184673", "194e7b", "153762", "1b4f7d", "153d67", "13325c", "153e69", "18416d", "16416c", "16436e", "163c67", "16406c", "163b65", "194470", "183e68", "153862", "1a517f", "16406a", "17416e", "15375f", "1f5482", "184b76", "153c68", "17446f", "1c507e", "17426c", "1c507d", "1a4f7d", "153761", "1b537e", "163a65", "163b64", "17416b", "1a507e", "133762", "184b77", "174973", "163f6a", "16416b", "174872", "174875", "173d68", "153a65", "163862", "16406d", "17456f", "1b5280", "193e66", "225e89", "215882", "235f8c", "225d89", "23628f", "1a436c", "23608c", "246592", "215d89", "225c88", "1c4670", "1e4b74", "183a62", "225883", "225983", "1c446c", "17345c", "18345d", "1e4c75", "1b4771", "1c426b", "193d65", "1e4c76", "215b86", "225f8c", "1a3f69", "1a3e69", "18355e", "1b4570", "1b406a", "183862", "205481", "1d456e", "1c4873", "1c446e", "215c89", "1c4972", "1c436b", "23638f", "183860", "1f4d78", "1c4069", "225c87", "214e77", "235983", "204e77", "1f466e", "245e88", "1e446b", "1f4d75", "1e466e", "235984", "204d73", "204d76", "204c74", "235780", "21557e", "1f466c", "214f77", "1f446c", "22517a", "245f88", "1f4c75", "235c85", "235a84", "1f5078", "20537c", "1f4972", "1f5079", "1c4269", "1c3f68", "1c4168", "1e4972", "1e446d", "225882", "1e456d", "215179", "22547d", "245b86", "1e436b", "205179", "21547d", "225580", "1d4974", "1f517e", "19416a", "1a4570", "1a4872", "183b63", "1b4e78", "1b4f79", "1d517f", "1d5581", "19426c", "1b4974", "1d5580", "1e5885", "19416c", "1c4c79", "20608f", "1d5583", "1d507d", "19446f", "193f6a", "1b4a78", "1c4b78", "19456f", "194770", "183d66", "1e5583", "18446e", "1b4d77", "1c4e78", "194772", "1c4d78", "194671", "173c67", "1a4774", "1d517e", "193d67", "183c67", "1c5583", "194773", "194771", "1c5582", "215684", "1e527f", "173760", "215683", "1e4b76", "1b436d", "1a416c", "1b4672", "19406a", "215785", "1c4975", "1b426d", "1e4c78", "225f8d", "215987", "1f537f", "1e4c77", "1b426c", "215a87", "1d4975", "1b416b", "173761", "183a64", "1b4973", "1a446d", "1f5a87", "1a3f68", "1d4b78", "193c66", "1e5280", "1f5886", "1c4874", "1b4c76", "1c4c76", "1a426d", "1b446f", "1e507d", "1e517e", "1c4c77", "1b4671", "1a436d", "205680", "1c547e" } );
	private static List<String> depletedTowerColors = Arrays.asList(new String[] { "424f6d", "4f5675", "4e5674", "4d5674", "4c5a76", "4c5773", "4c5b76", "4d5c77", "4f5d78", "ffc7fe", "ffeaff", "505a76", "505975", "515c7a", "505b75", "525873", "ffffff", "4f5b77", "505c78", "505c77", "505774", "4f5b75", "535e78", "525a78", "3d3836", "a62855", "4f5b78", "4f5775", "535e78", "535974", "505775", "525876", "525d7a", "4b587a", "4f5a77", "4b587e", "4b587b", "4b5a77", "4b5677", "4f5977", "4c5775", "4b587c", "47567d", "4d5b7c", "505974", "515875", "4d597b", "4c587b", "49577c", "4e597b", "48526c", "4c5672", "4b5571", "4c5673", "47506a", "49536e", "4b587d", "4f5873", "4b597b", "525975", "4e597a", "4b5a7c", "4d5776", "4b597d", "4f5876", "4d5673", "4d5979", "44506f", "4d5b7b", "4a577e", "4a5470", "4e5a78", "4b5471", "4f5a7b", "4f5a79", "48567d", "4d5774", "4b5879", "4f5979", "4e5975", "4e5876", "4f5a78", "4d5877", "505875", "484f69", "434f6e", "4a536f", "4d5b7d", "434e6d", "4b5c7a", "4c5b7b", "4d5a7a", "4c5a7b", "4d5775", "4d5c7e", "4f5974", "48567a", "4e5775", "464e66", "46557c", "49577e", "48526d", "465271", "455171", "4b5977", "49546f", "4b5978", "4d5876", "475373", "49506b", "4b5979", "4b5777", "525974", "515974", "4c5b7a", "4b5b7b", "4d556f", "4f5978", "4f5877", "4f5a76", "455170", "4e5977", "4f5a75", "4b5774", "4f587a", "424e6c", "4e556f", "4d5978", "4c5c7b", "45506f", "47597d", "4c5777", "4b5778", "475a7d", "47516b", "4c5677", "495574", "4d5a78", "535a75", "495370", "4d5778", "4d5976", "4b5878", "48577d", "515975", "4a597e", "4e5878", "4b5877", "4d5a79", "505874", "4e5a77", "4b5a78", "515874", "4f5a73", "475475", "475476", "525874", "4d5a77", "4e5776", "485578", "475474", "505873", "4d5878", "4d5678", "485579", "4e5979", "4d597a", "515773", "4c5a7a", "505877", "4d5977", "4c5678", "4e5877", "4e5976", "4f5874", "4f5973", "4c5979", "4b5b7d", "4e5978", "4f5975", "4c5879", "4b5779", "4c5977", "4c587a", "4f5774", "4f5673", "515776", "525875", "4c5a78", "4c5a79", "4f5878", "4e5a7b", "535875", "4c5a77", "465272", "4f5773", "505a74", "505776", "515a74", "505977", "495677", "4a5471", "4a5370", "4a5472", "4a5473", "4a5371", "495471", "46506b", "48597d", "48526e", "4b5574", "4b5674", "4d5a7d", "4c597e", "49587e", "454e66", "4e5c7a", "4d5b7a", "49597e", "4c5674", "4a587e", "465069", "454f69", "475876", "485876", "485875", "485976", "475777", "475676", "444f6e", "465373", "475575", "495977", "495979", "49597a", "485779", "475679", "4a577d", "4a5a7a", "4d5c7c", "495a7a", "4b5a7b", "454e6c", "464f67", "464f68", "444f6d", "515876", "4d5879", "4b5a7a", "4b5675", "4b5671", "4a536e", "4a5570", "4b5570", "495678", "495578", "4d566f", "4d5570", "4d5571", "4d5672", "4c566f", "4d5670", "4f5470", "4f566f", "4e566f", "49526d", "48506a", "49577d", "454d65", "474f67", "475069", "4c5a7e", "4c5776", "464e68", "4c5675", "47557b", "48526b", "4b5470", "4d5a7b", "495579", "495068", "485069", "424d6b", "4e5875", "4c5572", "4e5774", "4f5875", "4d5773" } );
	private static List<String> towerColors = Arrays.asList(new String[] { "b22757", "ffd8ff", "590808", "b65b72", "e92d65", "e2265e", "e0245c", "da265d", "b42757", "df265d", "c96b87", "cb6d89", "932249", "ca6c88", "d52458", "dd245b", "dd255c", "d52257", "b74669", "d62459", "97214b", "cb6c88", "ce5678", "cb6a89", "b22856", "b9476a", "cb6b87", "cb587a", "b8466a", "d8255b", "cc597b", "cd597b", "d9255b", "d42458", "b42856", "ca6a86", "d42257", "ac2552", "ce2256", "a54162", "a32854", "d1255a", "b02856", "b22656", "af2656", "d8245b", "b42656", "d7255a", "b32755", "c8587c", "d9245b", "de245b", "cd6b8a", "b94c6f", "bf4c6f", "9b395c", "c86985", "cb6a86", "c96985", "bd4a6e", "bc486d", "94224a", "922249", "95214a", "96224a", "ba476b", "8e2349", "cb6988", "cc6d88", "cd6e8a", "a24a6a", "912248", "c76c89", "ce5a7c", "a44a6a", "c66d89", "cd6b8b", "c76a88", "cd6b89", "a44b6a", "c86b86", "cc6e8a", "cf5779" } );
	private static ThreadSafeTowerBar tstb;
	private static ThreadSafeExitBattleButton tsebb;
	private static Point screenSize;
	private static Point battleTab;
	private static Point battleButton;
	private static Point twoVTwoButton;
	private static Point battleChat;
	private static Point exitBattle;
	private static Point exit2v2Battle;
	private static Point quick2v2Match;
	private static Point topLeftBattlefield;
	private static Point bottomRightBattlefield;
	private static Point chestsFullYes;
	private static Point close2v2Chat;
	private static Point confirmUnlock;
	private static Point center;
	private static Point leftTower;
	private static Point rightTower;
	private static Map<String, Point> chatOpts = new HashMap<String, Point>();
	private static Map<String, Point> cards = new HashMap<String, Point>();
	private static Map<String, Point> chestsSide = new HashMap<String, Point>();
	private static Map<String, Point> chestsMiddle = new HashMap<String, Point>();
	private static JadbConnection jadb;
	private static JadbDevice device;
	
	public static void main(String[] args){
		if(connectToDevice() && checkClashRoyaleInstalled()){
			launchClashRoyale();
			System.out.println("App loaded!");
			initializeButtons();
			for(int i = 0; i < 500; i++){
				openChests();
				startBattle(true);
			}
		}
		System.out.println("Done.");
		System.exit(0);
	}
	
	private static String consumeStream(InputStream s){
		try{
			return IOUtils.toString(s, Charset.defaultCharset());
		} catch (Exception e){
			return "";
		}
	}
	
	private static void initializeButtons(){
		screenSize = getScreenSize();
		battleTab = new Point((int)(screenSize.x * 0.5), (int)(screenSize.y * 0.96875));
		battleButton = new Point((int)(screenSize.x * 0.33), (int)(screenSize.y * 0.65));
		twoVTwoButton = new Point((int)(screenSize.x * 0.66), (int)(screenSize.y * 0.65));
		battleChat = new Point((int)(screenSize.x * 0.1), (int)(screenSize.y * 0.85));
		exitBattle = new Point((int)(screenSize.x * 0.5), (int)(screenSize.y * 0.895));
		exit2v2Battle = new Point((int)(screenSize.x * 0.1), (int)(screenSize.y * 0.95));
		quick2v2Match = new Point((int)(screenSize.x * 0.66), (int)(screenSize.y * 0.5625));
		topLeftBattlefield = new Point((int)(screenSize.x * 0.1), (int)(screenSize.y * 0.3));
		bottomRightBattlefield = new Point((int)(screenSize.x * 0.88), (int)(screenSize.y * 0.74));
		chestsFullYes = new Point((int)(screenSize.x * 0.5), (int)(screenSize.y * 0.605));
		close2v2Chat = new Point((int)(screenSize.x * 0.94), (int)(screenSize.y * 0.025));
		confirmUnlock = new Point((int)(screenSize.x * 0.5), (int)(screenSize.y * 0.65));
		center = new Point((int)(screenSize.x * 0.5), (int)(screenSize.y * 0.5));
		leftTower = new Point((int)(screenSize.x * 0.24), (int)(screenSize.y * 0.157));
		rightTower = new Point((int)(screenSize.x * 0.76), (int)(screenSize.y * 0.157));
		
		chatOpts.put("happy", new Point((int)(screenSize.x * 0.27), (int)(screenSize.y * 0.8)));
		chatOpts.put("angry", new Point((int)(screenSize.x * 0.5), (int)(screenSize.y * 0.8)));
		chatOpts.put("sad", new Point((int)(screenSize.x * 0.7), (int)(screenSize.y * 0.8)));
		chatOpts.put("funny", new Point((int)(screenSize.x * 0.9), (int)(screenSize.y * 0.8)));
		chatOpts.put("gl", new Point((int)(screenSize.x * 0.32), (int)(screenSize.y * 0.86)));
		chatOpts.put("wp", new Point((int)(screenSize.x * 0.6), (int)(screenSize.y * 0.86)));
		chatOpts.put("wow", new Point((int)(screenSize.x * 0.88), (int)(screenSize.y * 0.86)));
		chatOpts.put("ty", new Point((int)(screenSize.x * 0.32), (int)(screenSize.y * 0.91)));
		chatOpts.put("gg", new Point((int)(screenSize.x * 0.6), (int)(screenSize.y * 0.91)));
		chatOpts.put("oops", new Point((int)(screenSize.x * 0.88), (int)(screenSize.y * 0.91)));

		cards.put("1", new Point((int)(screenSize.x * 0.3125), (int)(screenSize.y * 0.9)));
		cards.put("2", new Point((int)(screenSize.x * 0.5), (int)(screenSize.y * 0.9)));
		cards.put("3", new Point((int)(screenSize.x * 0.6875), (int)(screenSize.y * 0.9)));
		cards.put("4", new Point((int)(screenSize.x * 0.875), (int)(screenSize.y * 0.9)));

		chestsSide.put("crown", new Point((int)(screenSize.x * 0.54), (int)(screenSize.y * 0.17)));
		chestsSide.put("1", new Point((int)(screenSize.x * 0.23), (int)(screenSize.y * 0.875)));
		chestsSide.put("2", new Point((int)(screenSize.x * 0.48), (int)(screenSize.y * 0.875)));
		chestsSide.put("3", new Point((int)(screenSize.x * 0.73), (int)(screenSize.y * 0.875)));
		chestsSide.put("4", new Point((int)(screenSize.x * 0.976), (int)(screenSize.y * 0.875)));
		
		chestsMiddle.put("1", new Point((int)(screenSize.x * 0.162), (int)(screenSize.y * 0.841)));
		chestsMiddle.put("2", new Point((int)(screenSize.x * 0.417), (int)(screenSize.y * 0.841)));
		chestsMiddle.put("3", new Point((int)(screenSize.x * 0.657), (int)(screenSize.y * 0.841)));
		chestsMiddle.put("4", new Point((int)(screenSize.x * 0.912), (int)(screenSize.y * 0.841)));
	}
	
	private static InputStream executeShell(String command) throws IOException, JadbException{
		int maxRetry = 3;
		for(int retry = 1; retry <= maxRetry; retry++){
			try{
				return device.executeShell(command);
			} catch (Exception ex){
				if (retry == maxRetry){
					throw ex;
				}
			}
		}
		return null;
	}
	
	private static String getPixelColor(boolean reuseSS, boolean delete, Point pixel){
		try{
			if(consumeStream(executeShell("ls /sdcard/screen.dump")).trim().equals("/sdcard/screen.dump")){
				if(!reuseSS){
					consumeStream(executeShell("rm /sdcard/screen.dump"));
					consumeStream(executeShell("screencap /sdcard/screen.dump"));
				}
			}else{
				consumeStream(executeShell("screencap /sdcard/screen.dump"));
			}
			screenSize = getScreenSize();
			int offset = screenSize.x*pixel.y+pixel.x+3;
			InputStream s = executeShell("dd if=/sdcard/screen.dump bs=4 count=1 skip=" + offset + " 2>/dev/null");
			String rgbHex = Hex.encodeHexString(IOUtils.toByteArray(s));
			if(rgbHex.length() > 6){
				rgbHex = rgbHex.substring(0,6);
			}
			if(!reuseSS && delete){
				consumeStream(executeShell("rm /sdcard/screen.dump"));
			}
			return rgbHex;
		}catch (Exception e){
			System.out.println("Error getting pixel color. The error is: " + e.getMessage());
			System.exit(1);
			return null;
		}
	}
	
	private static void openChests(){
		tap(battleTab);
		boolean first = true;
		boolean startUnlock = true;
		boolean chestUnlocked = false;
		for (Point chest : chestsSide.values()) {
			String rgbHex = getPixelColor(!first, false, chest);
			first = false;
		    if(openChestColors.contains(rgbHex)){
		    	System.out.println("Opening unlocked chest.");
		    	chestUnlocked = true;
		    	for(int i = 0; i <= 12; i++){
		    		tap(chest);
		    	}
		    }else if(unlockingChestColors.contains(rgbHex)){
		    	startUnlock = false;
		    	System.out.println("Chest unlock already in progress.");
		    }
		}
		if(startUnlock){
			List<Point> chestsMiddleShuffled = new ArrayList<Point>(chestsMiddle.values());
			Collections.shuffle(chestsMiddleShuffled);
			for(Point chest : chestsMiddleShuffled){
				String rgbHex = getPixelColor(!chestUnlocked, false, chest);
				chestUnlocked = false;
				if(!backgroundChests.contains(rgbHex)){
					System.out.println("Starting chest unlock.");
					tap(chest);
					tap(confirmUnlock);
					break;
				}
			}
		}
	}
	
	private static void startBattle(boolean twoVTwo){
		tstb = new ThreadSafeTowerBar();
		tsebb = new ThreadSafeExitBattleButton();
		tap(battleTab);
		tap((twoVTwo ? twoVTwoButton : battleButton));
		if(twoVTwo) tap(quick2v2Match);
		tap(chestsFullYes);
		sleep(3000);
		Date endTime = new Date(System.currentTimeMillis()+4*60000);
		System.out.println("Battle started! Assuming full OT, game ends at " + endTime + ".");
		tap(center);
		tap(center);
		tap(battleChat);
		tap(chatOpts.get("happy"));
		tap(battleChat);
		tap(chatOpts.get("gg"));
		Thread finishedChecker = new Thread(){
			public void run(){
				boolean gameEnded = false;
				while(!gameEnded){
					String rgbHex = getPixelColor(false, false, exitBattle);
					String rgbHex2v2 = getPixelColor(true, false, exit2v2Battle);
					String leftTowerRGB = getPixelColor(true, false, leftTower);
					String rightTowerRGB = getPixelColor(true, true, rightTower);
					if(towerColors.contains(leftTowerRGB)){
						tstb.setLeftTowerHalf(false);
						tstb.setLeftTowerDone(false);
					}else if(depletedTowerColors.contains(leftTowerRGB)){
						tstb.setLeftTowerHalf(true);
						tstb.setLeftTowerDone(false);
					}else{
						tstb.setLeftTowerHalf(true);
						tstb.setLeftTowerDone(true);
					}
					if(towerColors.contains(rightTowerRGB)){
						tstb.setRightTowerHalf(false);
						tstb.setRightTowerDone(false);
					}else if(depletedTowerColors.contains(rightTowerRGB)){
						tstb.setRightTowerHalf(true);
						tstb.setRightTowerDone(false);
					}else{
						tstb.setRightTowerHalf(true);
						tstb.setRightTowerDone(true);
					}
					gameEnded = okColors.contains(rgbHex) | okColors.contains(rgbHex2v2);
					if(gameEnded) tsebb.setFound(true);
				}
			}
		};
		finishedChecker.setDaemon(true);
		finishedChecker.start();
		while((new Date(System.currentTimeMillis())).getTime() < endTime.getTime()){
			drag(cards.get(String.valueOf(getRandomNumberInRange(1,4))), randomPoint(), getRandomNumberInRange(100,750));
			sleep(getRandomNumberInRange(300,1000));
			if(tsebb.getFound()){
				System.out.println("Battle ended early due to OK button being found!");
				break;
			}
		}
		if(twoVTwo) tap(close2v2Chat);
		tap(battleChat);
		tap(chatOpts.get("happy"));
		tap(battleChat);
		tap(chatOpts.get("gg"));
		sleep(1500);
		tap((twoVTwo ? exit2v2Battle : exitBattle));
		sleep(1500);
		System.out.println("Battle ended and exitted!");
	}
	
	private static void drag(Point from, Point where, int milliseconds){
		try{
			consumeStream(executeShell("input swipe " + from.x + " " + from.y + " " + where.x + " " + where.y + " " + milliseconds));
		} catch (Exception e) {
			System.out.println("An error sending swipe command occurred. The error message is: " + e.getMessage());
			System.exit(1);
		}
	}
	
	private static void tap(Point where){
		try{
			consumeStream(executeShell("input tap " + where.x + " " + where.y));
		} catch (Exception e) {
			System.out.println("An error sending tap command occurred. The error message is: " + e.getMessage());
			System.exit(1);
		}
	}
	
	private static boolean connectToDevice(){
		Utils.run("adb kill-server");
		Utils.run("adb start-server");
		jadb = new JadbConnection();
		Scanner scan = new Scanner(System.in);
		while(device == null){
			try {
				List<JadbDevice> deviceList = jadb.getDevices();
				System.out.println("Please choose a device number, or enter and IP Address. Port assumed to be 5555, unless specified like 0.0.0.0:5555.");
				int i = 1;
				for(JadbDevice d : deviceList){
					System.out.println(i + ") " + d.toString());
					i++;
				}
				System.out.print("> ");
				String choice = scan.next().trim();
				if(choice.matches("\\d+")){
					device = deviceList.get(Integer.parseInt(choice) - 1);
				}else{
					String ip;
					int port;
					if(choice.contains(":")){
						ip = choice.split(":")[0];
						try{
							port = Integer.parseInt(choice.split(":")[1]);
						} catch (Exception e){
							System.out.println("Invalid port value '" + choice.split(":")[1] + "'.");
							port = 5555;
						}
					}else{
						ip = choice;
						port = 5555;
					}
					InetSocketAddress inetSocketAddress = new InetSocketAddress(ip, port);
					try{
						jadb.connectToTcpDevice(inetSocketAddress);
						System.out.println("Successfully connected to device at '" + inetSocketAddress.getAddress().getHostAddress() + ":" + inetSocketAddress.getPort() + "'. It has been added to the list if accessible.");
					} catch (Exception e) {
						System.out.println("Cannot connect to device at '" + inetSocketAddress.getAddress().getHostAddress() + ":" + inetSocketAddress.getPort() + "'.");
					}
				}
			} catch (Exception e) {
				System.out.println("An error listing ADB devices occured. The error message is: " + e.getMessage());
				scan.close();
				return false;
			}
		}
		System.out.println("Successfully connected to " + device + ".");
		scan.close();
		return true;
	}
	
	private static boolean checkClashRoyaleInstalled(){
		PackageManager pm = new PackageManager(device);
		List<Package> packageList;
		try {
			packageList = pm.getPackages();
			for(Package p : packageList){
				if(p.toString().equals("com.supercell.clashroyale")) return true;
			}
			System.out.println("Clash Royale is not installed or cannot be found.");
			return false;
		} catch (Exception e) {
			System.out.println("Error listing packages. The error is: " + e.getMessage());
			return false;
		}
	}
	
	private static void launchClashRoyale(){
		try {
			consumeStream(executeShell("monkey -p com.supercell.clashroyale -c android.intent.category.LAUNCHER 1"));
			long startTime = System.currentTimeMillis();
			while(!consumeStream(executeShell("dumpsys activity top")).contains("android.webkit.WebView")){
				sleep(1);
				if((System.currentTimeMillis()-startTime) > 10000){
					System.out.println("Error launching Clash Royale in 30 seconds.");
					System.exit(1);
				}
			}
		} catch (Exception e) {
			System.out.println("Error launching Clash Royale. The error is: " + e.getMessage());
			System.exit(1);
		}
	}
	
	private static void sleep(int milliseconds){
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private static Point getScreenSize(){
		if(screenSize == null){
			try {
				String screenSizeRes = consumeStream(executeShell("wm size")).replaceAll("Physical size: ", "").trim();
				screenSize = new Point(Integer.parseInt(screenSizeRes.split("x")[0]),Integer.parseInt(screenSizeRes.split("x")[1]));
			} catch (Exception e) {
				System.out.println("Error getting window size. The error is: " + e.getMessage());
				System.exit(1);
			}
		}
		return screenSize;
	}
	
	private static Point randomPoint(){
	    int minX = topLeftBattlefield.x;
	    int minY = topLeftBattlefield.y;
	    int maxX = bottomRightBattlefield.x;
	    int maxY = bottomRightBattlefield.y;

    	int rndX = getRandomNumberInRange(minX, maxX);
    	int rndY = getRandomNumberInRange(minY, maxY);
	    if(tstb != null){
	    	if(!tstb.getLeftTowerDone() && tstb.getLeftTowerHalf() && !tstb.getRightTowerHalf()){
	    		rndX = getRandomNumberInRange(minX, maxX/2);
	    	}else if(!tstb.getRightTowerDone() && tstb.getRightTowerHalf() && !tstb.getLeftTowerHalf()){
	    		rndX = getRandomNumberInRange(maxX/2, maxX);
	    	}
	    }
    	return new Point(rndX, rndY);
	}
	
	private static int getRandomNumberInRange(int min, int max) {
		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}
		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
}
