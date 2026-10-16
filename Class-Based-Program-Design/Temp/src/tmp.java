import tester.*;
// TODO: import hashamp

// Represents one word in a dictionary, together with its definition
class DictEntry {
  String word;
  String meaning;
}

// Represents a Wiki entry
class WikiEntry {
  String url;
  String contents;
}


class Examples {
  Examples(){}

  void testHashMaps(Tester t) {
    HashMap<String, String> rooms = new HashMap<String, String>();
    // Put all the data into the hashtable
    rooms.put("Ben Lerner", "WVH314");
    rooms.put("Leena Razzaq", "WVH310B");
    rooms.put("Olin Shivers", "WVH308");
    rooms.put("Matthias Felleisen", "WVH308B");
    // Get the data
    t.checkExpect(rooms.get("Ben Lerner"), "WVH314");
    t.checkExpect(rooms.get("Olin Shivers"), "WVH310B");
    // Check that some data is present
    t.checkExpect(rooms.containsKey("Leena Razzaq"), true);
    t.checkExpect(rooms.containsKey("Amal Ahmed"), false);
    // Data that isn't present will return null
    t.checkExpect(rooms.get("Amal Ahmed"), null);
  }

}

