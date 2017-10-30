import java.awt.Point;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Main {
	
	private static List<String> okColors = Arrays.asList(new String[] { "4eafff", "68bbff", "6abcff", "67baff", "66b9ff", "65b9ff", "69bbff", "4caeff", "48acff", "4badff", "50b0ff", "4aadff", "0054a8", "4daeff", "4fb0ff", "4fafff", "32bcff", "096dd2", "8fb7cb", "61d0ff", "49adff", "a3ddff", "83d5ff", "218df9", "0053a7", "50b8ff", "46aaff", "103860", "004ea4", "2791fe", "3aa2ff", "218cf9", "49acff", "3aa1ff", "0050a0", "4eb5ff", "0053a6", "50b3ff", "50b7ff", "004fa3", "46abff", "49b8ff", "0058ae", "2790ff", "1e8bff", "1b8bfa", "1d87fe", "1b88fd", "1f89fa", "1f8afc" });
	private static List<String> openChestColors = Arrays.asList(new String[] { "ffe05d", "ffc846", "fec745", "fedf5c", "ffc946", "fedf5d", "fec845", "fec846", "fee05c", "ffe15d", "fee05d", "ffe05c", "ffdf5d", "fec946", "fec945", "ffca47", "ffc845", "fee15c", "ffca46", "feca46", "fee15d", "ffcb47", "fdc645", "fec746", "ffc746", "ffdf5c", "fecb46", "fece5e", "fed166", "f1d669", "fdc748", "f8da5f", "ecdc94", "fdc950", "fece5c", "bcab62", "fecc46", "c7b35f", "fecd47", "d9ca85", "e8d175", "fdc94c", "ffcd47", "fed673", "ffcc47", "fdc646", "feca47", "fdde5d", "fed36d", "fee5a3", "ffcc49", "ffbe3c", "ffbe3b", "ffcb48", "fecb48", "ffcb49", "ffcc48", "ffc240", "ffbf3d", "fdd98b", "febe3b", "ffc644", "ffc441", "ffc13f", "ffc341", "ffc947", "fdbd3c", "febd3b", "fecb49", "ffca48", "ffc13e", "ffbd3b", "ffc745", "ffc543", "f6d066", "fecc49", "ffcd4a", "fdc453", "e5bc64", "fccf55", "ffc744", "fdc75c", "aa812a", "b0862c", "b68a2d", "fdc045", "fcd889", "fec244", "d8b571", "f2cf6d", "fcc95b", "fdcd6b", "fdc658", "fdcd4b", "c4993d", "fad05d", "fec44a", "e7c367", "fdc24c", "fdc350", "fdbf42", "ffbf3c", "fec947", "ffc03e", "febe3c", "fdd689", "fdbd3e", "fdcd4f", "fdca65", "fdbd3b", "fdbe40", "fdbd3d", "fdd789", "fece69", "febf3d", "ffbd3c", "fcc860", "fec03d", "ffc03d", "fdcc68", "feca48", "fdcd65", "fdd98d", "fdd88a", "febd3c", "fdc148", "fec240", "fdca5c", "fec442", "ffc340", "fec140", "fec03e", "ffc442", "fbbd41", "fecc48", "fcd98d", "fcbc3b", "ffc140", "febf3c", "fcc148", "ffc544", "ffc542", "fec441", "fec643", "ffc241", "fec544", "aa822a", "fdc44a", "fcbd3e", "ffc145", "fcbd3f", "c59a3d", "fccd65", "b1872c", "fcbe40", "fdc860", "e7c466", "fccb4a", "fccc68", "f9d05d", "fdbc3c", "fec148", "fcc95c", "fcd789", "f2ce6c", "fec744", "b68b2d", "fbcc65", "fdbc3b", "facb64", "fcd88a", "fec644", "fcc24e", "fec13f", "fec03f", "f3d070", "fec543", "fdc54d", "e0c16f", "fcca65", "fcdfa1", "fac75a", "fcc456", "fcbd3d", "fcbc3d", "fdce69", "fdd688", "fccd4f", "fbdd9c", "fddf9f", "fccb67", "fcbc3c", "fecb47", "d7b570", "fdcb63", "fcd78a", "fcde9e", "fbcc50", "fdd88b", "fccc4b", "fbd059", "facf58", "fbcb4b", "fbcc4e", "fdd056", "fcd686", "fccf54", "f7d063", "fbcc4d", "fcd683", "fccd4e", "fcdd9b", "b28c3c", "fcd788", "fcdf9e", "fbd788", "dcb96d", "e5bb64", "dcb86d", "fcd98b", "fcd588", "d0b47d", "cfb47d", "d8b670", "fcd689", "fddd9c", "fcdc9d", "fbde9e", "9f7928", "b28d3c", "fcdd9c", "eecf77", "eece77", "fcd47d", "fdcd68", "fdd98c", "997426", "b78b2e", "a07a28", "fdd98a", "916e24", "987426", "916f24", "f8d063", "fbca68", "fccd6c", "fcbb3b", "fdd176", "fbbb3d", "fbbc3d", "fbd179", "fbbb3b", "fcbd3c", "fdc95f", "feca60", "fdc346", "fcc54f", "feca5f", "fbd074", "fdcc65", "fbbc3f", "fccd6d", "fcc24c", "fdc24d", "fcc24d", "fdcd6a", "fbc351", "fcc049", "fbc251", "fcc04a", "fec149", "fdbf45", "fcbf42", "fcbf44", "fcc350", "fdc149", "fccd6a", "fec345", "ffc343", "fac759", "fcc75c", "fdc14b", "fdc95c", "fdc657", "fac85a", "fcc75b", "fbca67", "fac758", "fbcb64", "fbcb63", "facf72", "fbd072", "fdcf72", "fecc64", "fbc85a", "fbc456", "fcd076", "fcce72", "fec34f", "fcc557", "fec242", "fdc960", "fcc75d", "fec553", "fbc862", "fbc861", "fccc67", "fbd076", "fdca5b", "fcd179", "fcc65a", "ffc94a", "ffd571", "ffe15c", "ffc945", "fecc56", "ffd048", "ffde5a", "ffe195", "ffce48", "ffe25d", "ffe25e", "fecc47", "ffe35e", "fece48", "ffd36d", "ffcf48", "ffd673", "fee25d", "ffdf5b", "ffd149", "ffce5c", "fecf48", "ffda56", "ffd148", "ffd955", "ffd652", "ffe05b", "ffe35c", "fee05b", "ffe15b", "fedf5b", "fee195", "fed14d", "ffd950", "ffdb57", "ffd24e", "fdde5c", "ffd34e", "ffdd5a", "ffd24a", "ffe5a3", "ffcd49", "ffd64f", "ffdd59", "ffd74f", "ffd750", "ffe05a", "fed852", "ffde57", "ffe057", "ffdd54", "fedd57", "fedb56", "fedc56", "ffd854", "feda53", "fedc57", "ffdc58", "ffd550", "ffdf59", "ffd551", "ffd54f", "ffdd58", "ffd450", "ffd44e", "fed249", "fecf47", "fece47", "fdcc56", "ffd44c", "fecf49", "fecf4a", "ffd049", "ffd04c", "fed04b", "ffe25b", "ffd84f", "ffe25c", "ffce47", "fedf5a", "ffda55", "fedd56", "fedf57", "fee25e", "ffdb56", "feda55", "ffdc56", "ffdf56", "ffd956", "ffdb55", "fede56", "ffdf58", "ffde59", "fee25b", "ffe159", "fedf59", "fee15a", "ffdf5a", "ffdc59", "ffe15a", "ffe059", "fee25c", "fedd59", "ffde58", "ffe45e", "ffe058", "fee35d", "fede58", "ffe35d", "fedc58", "fedb57", "fed54f", "ffd54c", "fece4b", "fecf4b", "fed24b", "ffd34b", "ffd24b", "ffce4a", "fed04a", "fed34c", "fed54c", "ffd44d", "ffd54d", "fed74e", "ffd34d", "fed24c", "fed64d", "fecf4c", "ffd14c", "fed54b", "fed34b", "ffcf47", "fecd48", "fed048", "ffcc46", "ffcb46", "fed571", "fcc646", "ffcd48", "ffcf49", "ffd04a", "fed14a", "fece49", "ffd44b", "ffd44a", "fecd49", "fed04d", "fed24d", "ffd753", "fed853", "fed953", "ffd653", "ffdb53", "ffd552", "fed752", "ffd953", "fedb54", "fedc54", "ffdc55", "ffdd55", "ffde55", "feda54", "ffda54", "fed753", "ffd754", "ffd954", "ffdc53", "ffda52", "ffd34f", "ffd44f", "fed650", "fed34f", "fed44e", "fed34d", "ffd54e", "fed64e", "ffd850", "feda51", "ffd651", "ffd751", "fed952", "ffd451", "ffdb52", "ffd851", "ffd951", "ffda51", "fed855" });
	private static List<String> unlockingChestColors = Arrays.asList(new String[] { "57e292", "54e193", "54e294", "56e191", "55e395", "53e092", "52dd8f", "56e090", "58e696", "53de90", "58e697", "55e294", "58e797", "55df8f", "56e496", "59e797", "54de8e", "59e898", "54dd8e", "55e090", "54e194", "53de91", "55de8f", "56e495", "56e395", "53df91", "55df90", "57e191", "55e394", "59e798", "54de8f", "57e393", "53df92", "52dd90", "54e293", "56e091", "56e596", "57e291", "52de90", "53e093", "53dd90", "57e192", "58e393", "59e999", "54df8f", "54e093", "59e494", "56e292", "53e193", "54e394", "54dd8d", "58e898", "5ae595", "52de8f", "52da8c", "58e292", "52dc8f", "57e597", "5ce797", "9aebbc", "57e494", "57e696", "57e596", "53dd8f", "53de8f", "55dd8f", "59e393", "52dc8e", "56e192", "5be696", "57e293", "56df90", "59e293", "58e596", "55e295", "52df91", "58e092", "55de8e", "57e697", "54df90", "58e798", "a8faca", "55e495", "58e192", "59e697", "8ef6bb", "57df91", "56e394", "54e092", "59e897", "55e293", "5be595", "56e190", "51dc8e", "9aeabc", "54d684", "54dc8e", "a9faca", "5ae394", "52d98b", "57d08a", "53dd8e", "5de898", "56e291", "53e294", "56df8f", "53db8d", "5ae494", "59e799", "59e998", "56de90", "56e396", "52df92", "54dc8d", "58e494", "55e091", "55e194", "53e192", "58e597", "52db8d", "52de91", "54df8e", "4fd182", "67eba1", "5ae495", "53e194", "57e091", "54d785", "57e394", "55e191", "57e496", "51d88a", "8ff7bb", "58e698", "54df91", "59e698", "53db8c", "55e08f", "57e497", "5be695", "55e190", "57e395", "58e598", "5ae594", "5ae999", "57e392", "59db90", "59e394", "59e597", "59e595", "52d88b", "68eca2", "55e092", "53dd8d", "5ce898", "55d988", "4fcd7e", "5ae696", "51dd8e", "58e897", "51d98b", "5ae899", "58e293", "56e595", "56e294", "57e090", "58e695", "57e797", "56e193", "52d585", "52d686", "5be796", "53d786", "54db8c", "53e293", "55da89", "57e595", "51d789", "53d787", "53d686", "59e292", "67eaa1", "56db8a", "55db89", "55e193", "54dd8f", "56e092", "5be99a", "54e395", "5ae898", "56de8f", "51db8d", "4fcd7d", "54df92", "55dc8a", "57e190", "56e597", "54e192", "5deb9b", "5ceb9b", "5ae493", "55e192", "5be495", "57e695", "58e899", "52db8e", "5de99b", "5ce695", "5ce696", "5de999", "58e191", "56e08f", "5bea9a", "53d887", "56e392", "5de899", "5dec9c", "5eec9c", "5cea9a", "50d183", "5ae799", "51da8d", "55e093", "55dd8e", "55e396", "52e091", "5de998", "53db8b", "5be797", "53dc8d", "54de90", "52e092", "55e496", "5be999", "5de99a", "5be494", "5de897", "51dd8f", "52dd8e", "59e495", "5ce899", "53de8e", "5ae695", "51d98c", "58db90", "67eca2", "59e796", "58e294", "53dc8c", "58e091", "58e496", "58e395", "57e294", "58e291", "5be596", "51d283", "5ce596", "59e493", "5eed9d", "56e194", "54dd8c", "59e699", "55dc8e", "53d98b", "5adc91", "56e295", "59e392", "56d989", "aafacb", "55df91", "58e497", "55dc8d", "56df91", "58e396", "54d586", "5ae89a", "5ae393", "59e496", "57e193", "57df90", "55de90", "5ce897", "50d485", "8ef7bb", "4fd082", "57d18b", "58e796", "52d88a", "53dc8f", "54e094", "56dc8a", "53e091", "51da8c", "50d284", "50d182", "55db8a", "50d384", "54e295", "54e091", "54d988", "54d987", "59ea9a", "59e899", "56e293", "58e595", "55dc8c", "59de93", "51dc8f", "55da88", "55dc8b", "59de94", "56e494", "53da89", "52df90", "58e998", "58e999", "57d38c", "54dc8c", "52d685", "51da8b", "4fd081", "53dc8e", "52d98c", "5ce799", "54de8d", "57d28c", "5ee394", "52dd91", "59e192", "57de8f", "5ce595", "5ae79a", "57da8a", "53da8a", "56da89", "56e393", "54da8a", "5deb9c", "62ed9e", "6cefa6", "53d988", "5adf8e", "55db88", "54d786", "5ce999", "5be392", "59dc8a", "58d18b", "5de89b", "6befa4", "61eb9d", "93f8be", "acfacc", "5cd58f", "5dea9b", "4fce7f", "5adf8d", "5be594", "5ceb9a", "57d48e", "5ee095", "57de90", "50d586", "59dd93", "57d38d", "94f8bf", "56dc8e", "54d383", "53e094", "57e092", "57e093", "56da8c", "53d98c", "58df91", "4fd384", "54db8a", "5ee998", "5ee999", "5eeb9a", "5de797", "6ce29d", "54d989", "5ce796", "63e398", "92d4b1", "5eeb9b", "5ce391", "63ee9f", "6df1a6", "95f9c0", "adface", "5fec9d", "5fed9d", "5bdd8a", "5ce18f", "5de796", "5eed9c", "53df93", "5ce798", "55d484", "57dc8c", "56d787", "57dd8e", "59e395", "59e294", "58e193", "5dd690", "5fe195", "58db89", "58dd92", "59e497", "59e598", "59e090", "58db8a", "58db8b", "56da8a", "5ae798", "5be89a", "5be799", "71db9e", "61e29a", "59ea99", "acfacd", "53df90", "5fea9b", "58e997", "69eda3", "91f8bd", "5ad38d", "59dc92", "58dc8b", "53d685", "50cf80", "58df8d", "56d987", "5cde93", "56db89", "51d486", "58db91", "4fcc7e", "58d488", "56dc8d", "50d082", "52dc90", "53d788", "52d181", "52d384", "54da8c", "50d283", "59e594", "90f7bd", "5ee99a", "52d787", "59d28c", "5bde92", "54da8b", "54d784", "57dc8b", "56d886", "57db8a", "5de799", "54db8d", "54dd90", "54d787", "54d889", "52da8d", "54d685", "53d585", "53dd91", "52d789", "51d080", "55dd8d", "54d687", "55d888", "55d989", "56d988", "57de8e", "55e292", "5de89a", "58e190", "53d989", "52d586", "53d282", "57d488", "54c57d", "5ce99a", "51d889", "58da8e", "56dd8c", "58e594", "58e493", "52d584", "5ce89b", "56cf89", "55ca82", "55d785", "53d888", "55e596", "55d98a", "68eba1", "5ae796", "55c882", "58df8e", "57da88", "58dd8c", "56ce83", "53da8c", "4fcc7d", "67eba2", "55df8e", "55e494", "58d18c", "5ce89a", "5bd48e", "5de094", "56dd8f", "54e08f", "abfacc", "92f8be", "8ff7bc", "59e696", "60eb9c", "6aeda4", "5ae08f", "6cf4a6", "6bf4a5", "6df7a9", "6df8a9", "6cf5a6", "6cf7a9", "6cf7a8", "4dd98c", "51dd90", "6cf8a9", "4cd78b", "6bf3a4", "4fdb8e", "6cf4a5", "4ad589", "48d488", "48d387", "62ea9c", "4bd68a", "2fb66b", "4ed98d", "60e89a", "69eb9b", "6cf3a6", "4cd88b", "6af2a3", "69f1a2", "5fe799", "52b57d", "429e69", "42b274", "2aae65", "2ab267", "378c5c", "72be92", "3cc67a", "3ec87c", "3fc87d", "2bb267", "39c378", "2db469", "2db46a", "2eb56a", "43ce82", "44cf83", "6cf8a8", "34bd73", "37c276", "46d184", "3dc77b", "43cd81", "3ac579", "6df7a8", "42cd80", "47d285", "50dc8f", "36c075", "40c97e", "3ac479", "40ca7f", "49d589", "38c277", "50db8f", "47d286", "41cb80", "45d084", "30b86d", "5ee698", "5ce496", "2cb369", "63eb9e", "65ed9f", "30b76c", "69f0a1", "67efa0", "2cb368", "5ae395", "32af6a", "54de91", "29b066", "2ab167", "2bb368", "29b166", "6bf4a6", "66ee9f", "6bf5a6", "30b96e", "32b86c", "30af62", "31ba6f", "2fb064", "38c378", "30b064", "31b366", "32af6b", "34bf74", "82d8a7", "83d9a8", "3ac478", "82d9a7", "32b66a", "37c176", "31b96e", "31ae62", "2ead60", "2fb76c", "36c176", "35bd71", "35c075", "37c175", "2eb66b", "3bc579", "2fb569", "35bf74", "2eb56b", "36c076", "37c076", "32b468", "4dd88c", "5be497", "5ce597", "5ae295", "59dd8e", "55d485", "59e193", "5ee598", "63eb9d", "69f0a2", "6bf4a4", "67efa1", "66eea0", "65ed9e", "52d587", "53d98a", "54d585", "53d384", "6bf3a6", "6bf5a5", "47d287", "47d186", "49d588", "4dd88b", "4dd98d", "4ac381", "46d185", "45d083", "40cb7f", "40c97d", "41cc80", "42cc80", "42cd81", "50db8e", "50dc8e", "69ed9d", "6aee9f", "6bf2a3", "6bf1a2", "6cf5a7", "6af0a0", "6bf3a5", "6cf6a8", "6cf8aa", "6cf6a7", "6aed9e", "6af1a1", "3cc77b" } );
	private static List<String> backgroundChests = Arrays.asList(new String[] { "235b86", "1f517b", "1f517c", "1e4f7a", "235c87", "1f507a", "1d4d78", "20547e", "235b85", "20547f", "1f527d", "1f537e", "225985", "205783", "235b87", "1f517a", "215680", "1f527c", "1f5682", "225781", "1f547e", "225a85", "235a85", "1e5480", "1e4e79", "1e5580", "1d4d77", "194975", "1e5581", "1c527e", "1f4f7a", "1d4c77", "1d537f", "1d5480", "20557f", "1e547f", "215984", "194974", "1f5581", "215580", "225881", "215883", "1c537e", "225a86", "1d5682", "1f507b", "1e5784", "1b517d", "1f5582", "20537e", "235c88", "1d5683", "1d547f", "1e4e7a", "235d88", "1f537d", "225884", "215681", "1e507a", "1b517e", "1f517d", "245c87", "1e5783", "205884", "205683", "205580", "225b86", "1f5683", "1a4a76", "215985", "215781", "205682", "215a86", "21557f", "1a4a75", "1e5582", "1d537e", "184874", "225b87", "1d4b76", "1d5582", "1a4975", "1d4e79", "1c527d", "215884", "1d4f7a", "205883", "1c5481", "1f5681", "205784", "1e537f", "184872", "235c86", "215986", "205984", "1b507d", "235881", "1d4f7b", "1e5481", "184974", "1d5684", "1c537f", "194973", "245d88", "205681", "1a507c", "225984", "205782", "1d527e", "1d5380", "1f5580", "1d5481", "184873", "1f5782", "215783", "20537d", "174773", "164672", "235882", "1f5481", "225885", "1f5883", "1a507d", "205882", "1d527f", "1e4e78", "1c517e", "1f527e", "164671", "215885", "1b4a76", "184875", "1a4d78", "1b527d", "21547f", "1b527e", "184975", "235b88", "225b85", "164772", "1c4f7b", "1d446c", "225b88", "215a85", "1b507c", "1e5785", "1c537d", "215784", "1a4f7b", "1b4b76", "1f4c74", "1d5482", "1e4a73", "1d4e78", "1e4f7b", "205985", "1a4976", "1f4a73", "1d507c", "1a4f7c", "174772", "1f5885", "174873", "1a4b76", "21517a", "194874", "194976", "1f4f79", "1e5682", "164773", "245d87", "1f5785", "1f5784", "18365e", "1e507c", "1e5683", "1f547f", "20557e", "1e537e", "1e547e", "1e4f79", "1b4a75", "174672", "194873", "194a75", "1f5884", "1c517c", "1c517d", "235985", "235a86", "164571", "235d87", "225680", "1d517d", "21567f", "225a87", "1d507b", "1e507b", "1f5680", "1f5480", "1b4e79", "1d527d", "1d507a", "194875", "205581", "1e5684", "1a4d79", "184772", "1c527f", "235e89", "21547e", "1d547e", "215a84", "1d4a75", "1b517c", "1b5380", "1f4b73", "1e456c", "1f4e78", "22557e", "19416b", "1f5783", "1b4872", "1e517a", "194570", "164673", "174673", "193f68", "194c78", "1d4d79", "225681", "22547e", "1e446c", "184973", "194069", "194a76", "205582", "1c507c", "225780", "1c4d79", "1b4f7b", "1c5482", "1d4e7a", "1a406a", "1c5380", "1a4e7b", "225a84", "1e517d", "1e4770", "1b527f", "1a426c", "1a4069", "1f507c", "205983", "1c4b77", "1b507b", "1c4e79", "164471", "1f5882", "1d4f79", "1e5380", "236693", "1c4c78", "1a4a74", "1c4973", "15365e", "184774", "1c4e7a", "1f4b74", "184069", "17426d", "19436e", "20517c", "194c79", "205781", "1a4a77", "174874", "1b4c78", "1a4b77", "1c4e7b", "20527d", "154672", "1d4770", "245f89", "1b4e7a", "205684", "225986", "1b4d78", "1b4f7c", "154471", "1a517d", "1a4771", "245e89", "194b75", "1e5782", "154571", "1e436a", "1e5482", "154572", "1e466d", "1a4772", "1e5681", "1b436b", "153b65", "1b446d", "205583", "235781", "205986", "184773", "1c4974", "19426d", "1c4a74", "1f5583", "174774", "1b4c77", "205079", "183d67", "1f466d", "184977", "15355e", "153760", "1a517c", "235d89", "1c5480", "1e4870", "205480", "163f69", "1b4169", "1a416b", "183a63", "1c4f7c", "1b436c", "173c66", "194f7c", "1b507e", "164570", "215682", "1b4b77", "1b4975", "173c65", "1d5280", "15345d", "1d4369", "18426d", "20507a", "1d456c", "19406b", "1c4169", "184976", "1d466f", "183962", "21537e", "1b4670", "1b4e7b", "18436d", "1a507b", "1a4873", "1b4b78", "1a517e", "173a63", "1a527f", "1d4f7c", "194c77", "1a4871", "194d7b", "1b4d7a", "1d4e7b", "19436f", "1b4b75", "18406b", "1b4a77", "1d517c", "1a4c78", "1c4f7a", "1c4f79", "183c66", "18446f", "184570", "1c507b", "1b4d79", "173b63", "1f5684", "184470", "19436d", "184670", "1a4671", "1a4b78", "194670", "1a4974", "1a4c79", "1e4d77", "194b76", "1c466e", "1b426a", "1f4d77", "1c4a73", "183b64", "18355d", "1d4a74", "1a426b", "1c456d", "1d4670", "1b537f", "193c65", "1b5481", "205a85", "1b446e", "174a76", "1b456f", "183861", "193d66", "236390", "1c4772", "18375e", "1e4d78", "143660", "18375f", "1c436c", "1b4873", "15335d", "22567f", "164572", "20517a", "163a64", "245c88", "245c86", "235e88", "245f8a", "194e7c", "204d75", "1f456c", "154470", "1d426a", "21537d", "18426c", "1b5480", "15355d", "1e4871", "1a416a", "1c426a", "1f4f78", "1f4a72", "1d4269", "1d436a", "1f4971", "20527c", "143b66", "1c4671", "173d66", "15365f", "1b4772", "193f69", "1a436e", "235a87", "17406a", "1a4c76", "184a76", "163f68", "193b64", "1c4a75", "1e527d", "173d67", "1f5987", "17426e", "163f6b", "194d79", "1b456e", "1a456f", "1a3e68", "19446e", "1c4a76", "1d4b77", "153c67", "164470", "184b78", "163d68", "173b65", "194d7a", "1c5381", "174571", "184a77", "1b4976", "174670", "17406b", "194a77", "16355e", "173f69", "194c76", "174470", "163964", "17406c", "143863", "153964", "14335d", "16406b", "1a5280", "1b517f", "19507c", "163d69", "194b78", "184673", "194e7b", "153762", "1b4f7d", "153d67", "13325c", "153e69", "18416d", "16416c", "16436e", "163c67", "16406c", "163b65", "194470", "183e68", "153862", "1a517f", "16406a", "17416e", "15375f", "1f5482", "184b76", "153c68", "17446f", "1c507e", "17426c", "1c507d", "1a4f7d", "153761", "1b537e", "163a65", "163b64", "17416b", "1a507e", "133762", "184b77", "174973", "163f6a", "16416b", "174872", "174875", "173d68", "153a65", "163862", "16406d", "17456f", "1b5280", "193e66", "225e89", "215882", "235f8c", "225d89", "23628f", "1a436c", "23608c", "246592", "215d89", "225c88", "1c4670", "1e4b74", "183a62", "225883", "225983", "1c446c", "17345c", "18345d", "1e4c75", "1b4771", "1c426b", "193d65", "1e4c76", "215b86", "225f8c", "1a3f69", "1a3e69", "18355e", "1b4570", "1b406a", "183862", "205481", "1d456e", "1c4873", "1c446e", "215c89", "1c4972", "1c436b", "23638f", "183860", "1f4d78", "1c4069", "225c87", "214e77", "235983", "204e77", "1f466e", "245e88", "1e446b", "1f4d75", "1e466e", "235984", "204d73", "204d76", "204c74", "235780", "21557e", "1f466c", "214f77", "1f446c", "22517a", "245f88", "1f4c75", "235c85", "235a84", "1f5078", "20537c", "1f4972", "1f5079", "1c4269", "1c3f68", "1c4168", "1e4972", "1e446d", "225882", "1e456d", "215179", "22547d", "245b86", "1e436b", "205179", "21547d", "225580", "1d4974", "1f517e", "19416a", "1a4570", "1a4872", "183b63", "1b4e78", "1b4f79", "1d517f", "1d5581", "19426c", "1b4974", "1d5580", "1e5885", "19416c", "1c4c79", "20608f", "1d5583", "1d507d", "19446f", "193f6a", "1b4a78", "1c4b78", "19456f", "194770", "183d66", "1e5583", "18446e", "1b4d77", "1c4e78", "194772", "1c4d78", "194671", "173c67", "1a4774", "1d517e", "193d67", "183c67", "1c5583", "194773", "194771", "1c5582", "215684", "1e527f", "173760", "215683", "1e4b76", "1b436d", "1a416c", "1b4672", "19406a", "215785", "1c4975", "1b426d", "1e4c78", "225f8d", "215987", "1f537f", "1e4c77", "1b426c", "215a87", "1d4975", "1b416b", "173761", "183a64", "1b4973", "1a446d", "1f5a87", "1a3f68", "1d4b78", "193c66", "1e5280", "1f5886", "1c4874", "1b4c76", "1c4c76", "1a426d", "1b446f", "1e507d", "1e517e", "1c4c77", "1b4671", "1a436d", "205680", "1c547e" } );
	private static List<String> depletedTowerColors = Arrays.asList(new String[] { "424f6d", "4f5675", "4e5674", "4d5674", "4c5a76", "4c5773", "4c5b76", "4d5c77", "4f5d78", "ffc7fe", "ffeaff", "505a76", "505975", "515c7a", "505b75", "525873", "ffffff", "4f5b77", "505c78", "505c77", "505774", "4f5b75", "535e78", "525a78", "3d3836", "a62855", "4f5b78", "4f5775", "535e78", "535974", "505775", "525876", "525d7a", "4b587a", "4f5a77", "4b587e", "4b587b", "4b5a77", "4b5677", "4f5977", "4c5775", "4b587c", "47567d", "4d5b7c", "505974", "515875", "4d597b", "4c587b", "49577c", "4e597b", "48526c", "4c5672", "4b5571", "4c5673", "47506a", "49536e", "4b587d", "4f5873", "4b597b", "525975", "4e597a", "4b5a7c", "4d5776", "4b597d", "4f5876", "4d5673", "4d5979", "44506f", "4d5b7b", "4a577e", "4a5470", "4e5a78", "4b5471", "4f5a7b", "4f5a79", "48567d", "4d5774", "4b5879", "4f5979", "4e5975", "4e5876", "4f5a78", "4d5877", "505875", "484f69", "434f6e", "4a536f", "4d5b7d", "434e6d", "4b5c7a", "4c5b7b", "4d5a7a", "4c5a7b", "4d5775", "4d5c7e", "4f5974", "48567a", "4e5775", "464e66", "46557c", "49577e", "48526d", "465271", "455171", "4b5977", "49546f", "4b5978", "4d5876", "475373", "49506b", "4b5979", "4b5777", "525974", "515974", "4c5b7a", "4b5b7b", "4d556f", "4f5978", "4f5877", "4f5a76", "455170", "4e5977", "4f5a75", "4b5774", "4f587a", "424e6c", "4e556f", "4d5978", "4c5c7b", "45506f", "47597d", "4c5777", "4b5778", "475a7d", "47516b", "4c5677", "495574", "4d5a78", "535a75", "495370", "4d5778", "4d5976", "4b5878", "48577d", "515975", "4a597e", "4e5878", "4b5877", "4d5a79", "505874", "4e5a77", "4b5a78", "515874", "4f5a73", "475475", "475476", "525874", "4d5a77", "4e5776", "485578", "475474", "505873", "4d5878", "4d5678", "485579", "4e5979", "4d597a", "515773", "4c5a7a", "505877", "4d5977", "4c5678", "4e5877", "4e5976", "4f5874", "4f5973", "4c5979", "4b5b7d", "4e5978", "4f5975", "4c5879", "4b5779", "4c5977", "4c587a", "4f5774", "4f5673", "515776", "525875", "4c5a78", "4c5a79", "4f5878", "4e5a7b", "535875", "4c5a77", "465272", "4f5773", "505a74", "505776", "515a74", "505977", "495677", "4a5471", "4a5370", "4a5472", "4a5473", "4a5371", "495471", "46506b", "48597d", "48526e", "4b5574", "4b5674", "4d5a7d", "4c597e", "49587e", "454e66", "4e5c7a", "4d5b7a", "49597e", "4c5674", "4a587e", "465069", "454f69", "475876", "485876", "485875", "485976", "475777", "475676", "444f6e", "465373", "475575", "495977", "495979", "49597a", "485779", "475679", "4a577d", "4a5a7a", "4d5c7c", "495a7a", "4b5a7b", "454e6c", "464f67", "464f68", "444f6d", "515876", "4d5879", "4b5a7a", "4b5675", "4b5671", "4a536e", "4a5570", "4b5570", "495678", "495578", "4d566f", "4d5570", "4d5571", "4d5672", "4c566f", "4d5670", "4f5470", "4f566f", "4e566f", "49526d", "48506a", "49577d", "454d65", "474f67", "475069", "4c5a7e", "4c5776", "464e68", "4c5675", "47557b", "48526b", "4b5470", "4d5a7b", "495579", "495068", "485069", "424d6b", "4e5875", "4c5572", "4e5774", "4f5875", "4d5773" } );
	private static List<String> towerColors = Arrays.asList(new String[] { "b22757", "ffd8ff", "590808", "b65b72", "e92d65", "e2265e", "e0245c", "da265d", "b42757", "df265d", "c96b87", "cb6d89", "932249", "ca6c88", "d52458", "dd245b", "dd255c", "d52257", "b74669", "d62459", "97214b", "cb6c88", "ce5678", "cb6a89", "b22856", "b9476a", "cb6b87", "cb587a", "b8466a", "d8255b", "cc597b", "cd597b", "d9255b", "d42458", "b42856", "ca6a86", "d42257", "ac2552", "ce2256", "a54162", "a32854", "d1255a", "b02856", "b22656", "af2656", "d8245b", "b42656", "d7255a", "b32755", "c8587c", "d9245b", "de245b", "cd6b8a", "b94c6f", "bf4c6f", "9b395c", "c86985", "cb6a86", "c96985", "bd4a6e", "bc486d", "94224a", "922249", "95214a", "96224a", "ba476b", "8e2349", "cb6988", "cc6d88", "cd6e8a", "a24a6a", "912248", "c76c89", "ce5a7c", "a44a6a", "c66d89", "cd6b8b", "c76a88", "cd6b89", "a44b6a", "c86b86", "cc6e8a", "cf5779" } );
	private static String ADB_DEVICE_IP = "10.0.1.18";
	private static ThreadSafeVar tsv;
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
	
	public static void main(String[] args){
		if(connectToDevice() && checkClashRoyaleInstalled()){
			launchClashRoyale();
			System.out.println("App loaded!");
			initializeButtons();
			for(int i = 0; i < 1; i++){
				openChests();
				startBattle(false);
			}
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
		chestsSide.put("1", new Point((int)(screenSize.x * 0.12), (int)(screenSize.y * 0.763)));
		chestsSide.put("2", new Point((int)(screenSize.x * 0.38), (int)(screenSize.y * 0.763)));
		chestsSide.put("3", new Point((int)(screenSize.x * 0.64), (int)(screenSize.y * 0.763)));
		chestsSide.put("4", new Point((int)(screenSize.x * 0.9), (int)(screenSize.y * 0.763)));
		
		chestsMiddle.put("1", new Point((int)(screenSize.x * 0.162), (int)(screenSize.y * 0.841)));
		chestsMiddle.put("2", new Point((int)(screenSize.x * 0.417), (int)(screenSize.y * 0.841)));
		chestsMiddle.put("3", new Point((int)(screenSize.x * 0.657), (int)(screenSize.y * 0.841)));
		chestsMiddle.put("4", new Point((int)(screenSize.x * 0.912), (int)(screenSize.y * 0.841)));
	}
	
	private static String getPixelColor(Point pixel){
		return getPixelColor(false, true, pixel);
	}
	
	private static String getPixelColor(boolean reuseSS, boolean delete, Point pixel){
		if(Utils.run("adb shell ls /sdcard/screen.dump").trim().equals("/sdcard/screen.dump")){
			if(!reuseSS){
				Utils.run("adb shell rm /sdcard/screen.dump");
				Utils.run("adb shell screencap /sdcard/screen.dump");
			}
		}else{
			Utils.run("adb shell screencap /sdcard/screen.dump");
		}
		screenSize = getScreenSize();
		int offset = screenSize.x*pixel.y+pixel.x+3;
		String rgbHex = Utils.run("adb shell dd if=/sdcard/screen.dump bs=4 count=1 skip=" + offset + " 2>/dev/null | hexdump").substring(8, 16).replaceAll(" ","");
		if(!reuseSS && delete){
			Utils.run("adb shell rm /sdcard/screen.dump");
		}
		return rgbHex;
	}
	
	private static void openChests(){
		tap(battleTab);
		boolean first = true;
		boolean startUnlock = true;
		for (Point chest : chestsSide.values()) {
			String rgbHex = getPixelColor(!first, false, chest);
			first = false;
		    if(openChestColors.contains(rgbHex)){
		    	System.out.println("Opening unlocked chest.");
		    	for(int i = 0; i <= 12; i++){
		    		tap(chest);
		    	}
		    }else if(unlockingChestColors.contains(rgbHex)){
		    	startUnlock = false;
		    	System.out.println("Chest unlock already in progress.");
		    }
		}
		if(startUnlock){
			for(Point chest : chestsMiddle.values()){
				String rgbHex = getPixelColor(true, false, chest);
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
		tsv = new ThreadSafeVar();
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
					//System.out.println("Left tower RGB: " + leftTowerRGB + " at " + leftTower.x + "," + leftTower.y);
					//System.out.println("Right tower RGB: " + rightTowerRGB + " at " + rightTower.x + "," + rightTower.y);
					if(towerColors.contains(leftTowerRGB)){
						tsv.setLeftTowerHalf(false);
						tsv.setLeftTowerDone(false);
					}else if(depletedTowerColors.contains(leftTowerRGB)){
						tsv.setLeftTowerHalf(true);
						tsv.setLeftTowerDone(false);
						System.out.println("Left tower half!" + leftTowerRGB);
					}else{
						tsv.setLeftTowerHalf(true);
						tsv.setLeftTowerDone(true);
						System.out.println("Left tower done!" + leftTowerRGB);
					}
					if(towerColors.contains(rightTowerRGB)){
						tsv.setRightTowerHalf(false);
						tsv.setRightTowerDone(false);
					}else if(depletedTowerColors.contains(rightTowerRGB)){
						tsv.setRightTowerHalf(true);
						tsv.setRightTowerDone(false);
						System.out.println("Right tower half!" + rightTowerRGB);
					}else{
						tsv.setRightTowerHalf(true);
						tsv.setRightTowerDone(true);
						System.out.println("Right tower done!" + rightTowerRGB);
					}
					gameEnded = okColors.contains(rgbHex) | okColors.contains(rgbHex2v2);
				}
			}
		};
		finishedChecker.setDaemon(true);
		finishedChecker.start();
		while((new Date(System.currentTimeMillis())).getTime() < endTime.getTime()){ //Battle ends if back button does not exit
			drag(cards.get(String.valueOf(getRandomNumberInRange(1,4))), randomPoint(), getRandomNumberInRange(100,750));
			sleep(getRandomNumberInRange(300,1000));
			if(!finishedChecker.isAlive()){
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
		Utils.run("adb shell input swipe " + from.x + " " + from.y + " " + where.x + " " + where.y + " " + milliseconds);
	}
	
	private static void tap(Point where){
		Utils.run("adb shell input tap " + where.x + " " + where.y);
	}
	
	private static boolean connectToDevice(){
		String ip = ADB_DEVICE_IP;
		if(ip.isEmpty()){
			System.out.print("Enter IP address to ADB into(or type 'usb'): ");
			Scanner scan = new Scanner(System.in);
			ip = scan.next();
			scan.close();
		}
		if(!ip.toLowerCase().trim().equals("usb")){
			String connectResult = Utils.run("adb connect " + ip);
			if(connectResult.contains("connected to ")){
				System.out.println("Successfully connected to " + ip + "!");
				return true;
			}else{
				System.out.println("An error connecting occurred. The error is:\n" + connectResult);
			}
		}else{
			String connectResult = Utils.run("adb usb");
			if(connectResult.contains("restarting in USB mode")){
				System.out.println("Successfully connected to USB device!");
				return true;
			}else{
				System.out.println("An error connecting occurred. The error is:\n" + connectResult);
			}
		}
		return false;
	}
	
	private static boolean checkClashRoyaleInstalled(){
		String packageList = Utils.run("adb shell pm list packages");
		if(packageList.contains("com.supercell.clashroyale")){
			return true;
		}else{
			System.out.println("Clash Royale is not installed or cannot be found.");
			System.out.println(packageList);
		}
		return false;
	}
	
	private static void launchClashRoyale(){
		Utils.run("adb shell monkey -p com.supercell.clashroyale -c android.intent.category.LAUNCHER 1");
		while(!Utils.run("adb shell dumpsys activity top").contains("android.webkit.WebView")){
			sleep(1);
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
			String screenSizeRes = Utils.run("adb shell wm size").replaceAll("Physical size: ", "").trim();
			return new Point(Integer.parseInt(screenSizeRes.split("x")[0]),Integer.parseInt(screenSizeRes.split("x")[1]));
		}else{
			return screenSize;
		}
	}
	
	private static Point randomPoint(){
	    int minX = topLeftBattlefield.x;
	    int minY = topLeftBattlefield.y;
	    int maxX = bottomRightBattlefield.x;
	    int maxY = bottomRightBattlefield.y;

	    int rndX = getRandomNumberInRange(minX, maxX);
	    int rndY = getRandomNumberInRange(minY, maxY);
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
