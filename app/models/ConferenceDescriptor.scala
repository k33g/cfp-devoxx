package models

import play.api.Play
import org.joda.time.{DateTimeZone, DateTime}

/**
 * ConferenceDescriptor.
 * This might be the first file to look at, and to customize.
 * Idea behind this file is to try to collect all configurable parameters for a conference.
 *
 * For labels, please do customize messages and messages.fr
 *
 * @author Frederic Camblor
 */

case class ConferenceUrls(faq: String, registration: String,
                          confWebsite: String, cfpHostname: String
                           )

case class ConferenceTiming(
                             datesI18nKey: String,
                             speakersPassDuration: Integer,
                             preferredDayEnabled: Boolean,
                             firstDayFr: String,
                             firstDayEn: String,
                             datesFr: String,
                             datesEn: String,
                             cfpOpenedOn: DateTime,
                             cfpClosedOn: DateTime,
                             scheduleAnnouncedOn: DateTime
                             )

case class ConferenceSponsor(showSponsorProposalCheckbox: Boolean, sponsorProposalType: ProposalType = ProposalType.UNKNOWN)

case class TrackDesc(id: String, imgSrc: String, i18nTitleProp: String, i18nDescProp: String)

case class ProposalConfiguration(id: String, slotsCount: Int,
                                 givesSpeakerFreeEntrance: Boolean,
                                 freeEntranceDisplayed: Boolean,
                                 htmlClass: String,
                                 hiddenInCombo: Boolean = false,
                                 chosablePreferredDay: Boolean = false,
                                 impliedSelectedTrack: Option[Track] = None)

object ProposalConfiguration {

  val UNKNOWN = ProposalConfiguration(id = "unknown", slotsCount = 0, givesSpeakerFreeEntrance = false, freeEntranceDisplayed = false,
    htmlClass = "", hiddenInCombo = true, chosablePreferredDay = false)

  def parse(propConf: String): ProposalConfiguration = {
    ConferenceDescriptor.ConferenceProposalConfigurations.ALL.find(p => p.id == propConf).getOrElse(ProposalConfiguration.UNKNOWN)
  }

  def totalSlotsCount = ConferenceDescriptor.ConferenceProposalConfigurations.ALL.map(_.slotsCount).sum

  def isDisplayedFreeEntranceProposals(pt: ProposalType): Boolean = {
    ConferenceDescriptor.ConferenceProposalConfigurations.ALL.filter(p => p.id == pt.id).map(_.freeEntranceDisplayed).headOption.getOrElse(false)
  }

  def getProposalsImplyingATrackSelection = {
    ConferenceDescriptor.ConferenceProposalConfigurations.ALL.filter(p => p.impliedSelectedTrack.nonEmpty)
  }

  def getHTMLClassFor(pt: ProposalType): String = {
    ConferenceDescriptor.ConferenceProposalConfigurations.ALL.filter(p => p.id == pt.id).map(_.htmlClass).headOption.getOrElse("unknown")
  }

  def isChosablePreferredDaysProposals(pt: ProposalType): Boolean = {
    ConferenceDescriptor.ConferenceProposalConfigurations.ALL.filter(p => p.id == pt.id).map(_.chosablePreferredDay).headOption.getOrElse(false)
  }

  def doesProposalTypeGiveSpeakerFreeEntrance(pt: ProposalType): Boolean = {
    ConferenceDescriptor.ConferenceProposalConfigurations.ALL.filter(p => p.id == pt.id).map(_.givesSpeakerFreeEntrance).headOption.getOrElse(false)
  }
}

case class ConferenceDescriptor(eventCode: String,
                                confUrlCode: String,
                                frLangEnabled: Boolean,
                                fromEmail: String,
                                committeeEmail: String,
                                bccEmail: Option[String],
                                bugReportRecipient: String,
                                conferenceUrls: ConferenceUrls,
                                timing: ConferenceTiming,
                                hosterName: String,
                                hosterWebsite: String,
                                hashTag: String,
                                conferenceSponsor: ConferenceSponsor,
                                locale: List[String],
                                localisation: String,
                                showQuestion: Boolean
                                 )

object ConferenceDescriptor {

  object ConferenceProposalTypes {
    val CONF = ProposalType(id = "conf", label = "conf.label")

    val UNI = ProposalType(id = "uni", label = "uni.label")

    val TIA = ProposalType(id = "tia", label = "tia.label")

    val LAB = ProposalType(id = "lab", label = "lab.label")

    val QUICK = ProposalType(id = "quick", label = "quick.label")

    val BOF = ProposalType(id = "bof", label = "bof.label")

    val KEY = ProposalType(id = "key", label = "key.label")

    val HACK = ProposalType(id = "hack", label = "hack.label")

    val CODE = ProposalType(id = "cstory", label = "code.label")

    val OTHER = ProposalType(id = "other", label = "other.label")

    val STARTUP = ProposalType(id = "start", label = "start.label")

    val ALL = List(CONF, UNI, TIA, LAB, QUICK, BOF, KEY, HACK, OTHER, STARTUP)

    def valueOf(id: String): ProposalType = id match {
      case "conf" => CONF
      case "uni" => UNI
      case "tia" => TIA
      case "lab" => LAB
      case "quick" => QUICK
      case "bof" => BOF
      case "key" => KEY
      case "hack" => HACK
      case "cstory" => CODE
      case "other" => OTHER
    }

  }

  object ConferenceProposalConfigurations {
    val CONF = ProposalConfiguration(id = "conf", slotsCount = 89, givesSpeakerFreeEntrance = true, freeEntranceDisplayed = true, htmlClass = "icon-microphone",
      chosablePreferredDay = true)
    val UNI = ProposalConfiguration(id = "uni", slotsCount = 16, givesSpeakerFreeEntrance = true, freeEntranceDisplayed = true, htmlClass = "icon-laptop",
      chosablePreferredDay = true)
    val TIA = ProposalConfiguration(id = "tia", slotsCount = 24, givesSpeakerFreeEntrance = true, freeEntranceDisplayed = true, htmlClass = "icon-legal",
      chosablePreferredDay = true)
    val LAB = ProposalConfiguration(id = "lab", slotsCount = 10, givesSpeakerFreeEntrance = true, freeEntranceDisplayed = true, htmlClass = "icon-beaker",
      chosablePreferredDay = true)
    val QUICK = ProposalConfiguration(id = "quick", slotsCount = 28, givesSpeakerFreeEntrance = false, freeEntranceDisplayed = false, htmlClass = "icon-fast-forward",
      chosablePreferredDay = true)
    val BOF = ProposalConfiguration(id = "bof", slotsCount = 25, givesSpeakerFreeEntrance = false, freeEntranceDisplayed = false, htmlClass = "icon-group",
      chosablePreferredDay = false)
    val KEY = ProposalConfiguration(id = "key", slotsCount = 8, givesSpeakerFreeEntrance = true, freeEntranceDisplayed = false, htmlClass = "icon-microphone",
      chosablePreferredDay = true)
    val HACK = ProposalConfiguration(id = "hack", slotsCount = 1, givesSpeakerFreeEntrance = false, freeEntranceDisplayed = false, htmlClass = "icon-microphone",
      chosablePreferredDay = false)
    val CODE = ProposalConfiguration(id = "cstory", slotsCount = 1, givesSpeakerFreeEntrance = false, freeEntranceDisplayed = false, htmlClass = "icon-microphone",
      chosablePreferredDay = false)
    val STARTUP = ProposalConfiguration(id = "start", slotsCount = 10, givesSpeakerFreeEntrance = false, freeEntranceDisplayed = false, htmlClass = "icon-microphone",
      hiddenInCombo = true, chosablePreferredDay = false)    
    val OTHER = ProposalConfiguration(id = "other", slotsCount = 1, givesSpeakerFreeEntrance = false, freeEntranceDisplayed = false, htmlClass = "icon-microphone",
      hiddenInCombo = true, chosablePreferredDay = false)
    val ALL = List(CONF, UNI, TIA, LAB, QUICK, BOF, KEY, HACK, CODE, OTHER, STARTUP)

    def doesItGivesSpeakerFreeEntrance(proposalType: ProposalType): Boolean = {
      ALL.filter(_.id == proposalType.id).exists(_.givesSpeakerFreeEntrance)
    }
  }

  object ConferenceTracks {
    val WEB_MOBILE = Track("wm", "webmobile.label")
    val ARCHISEC = Track("archisec", "archisec.label")
    val AGILITY_TESTS = Track("agTest", "agilityTest.label")
    val JAVA = Track("java", "java.label")
    val CLOUDDEVOPS = Track("cldops", "cloudDevops.label")
    val BIGDATA = Track("bigd", "bigdata.label")
    val FUTURE = Track("future", "future.label")
    val LANG = Track("lang", "lang.label")
    val UNKNOWN = Track("unknown", "unknown track")
    val ALL = List(WEB_MOBILE, ARCHISEC, AGILITY_TESTS, JAVA, CLOUDDEVOPS, BIGDATA, FUTURE, LANG, UNKNOWN)
  }

  object ConferenceTracksDescription {
    val WEB_MOBILE = TrackDesc(ConferenceTracks.WEB_MOBILE.id, "/assets/devoxxbe2014/images/icon_web.png", "track.webmobile.title", "track.webmobile.desc")
    val ARCHISEC = TrackDesc(ConferenceTracks.ARCHISEC.id, "/assets/devoxxbe2014/images/icon_architecture.png", "track.archisec.title", "track.archisec.desc")
    val AGILITY_TESTS = TrackDesc(ConferenceTracks.AGILITY_TESTS.id, "/assets/devoxxbe2014/images/icon_startup.png", "track.agilityTest.title", "track.agilityTest.desc")
    val JAVA = TrackDesc(ConferenceTracks.JAVA.id, "/assets/devoxxbe2014/images/icon_javase.png", "track.java.title", "track.java.desc")
    val CLOUDDEVOPS = TrackDesc(ConferenceTracks.CLOUDDEVOPS.id, "/assets/devoxxbe2014/images/icon_cloud.png", "track.cloudDevops.title", "track.cloudDevops.desc")
    val BIGDATA = TrackDesc(ConferenceTracks.BIGDATA.id, "/assets/devoxxbe2014/images/icon_mobile.png", "track.bigdata.title", "track.bigdata.desc")
    val FUTURE = TrackDesc(ConferenceTracks.FUTURE.id, "/assets/devoxxbe2014/images/icon_future.png", "track.future.title", "track.future.desc")
    val LANG = TrackDesc(ConferenceTracks.LANG.id, "/assets/devoxxbe2014/images/icon_alternative.png", "track.lang.title", "track.lang.desc")
    val ALL = List(WEB_MOBILE, ARCHISEC, AGILITY_TESTS, JAVA, CLOUDDEVOPS, BIGDATA, FUTURE, LANG)

    def findTrackDescFor(t: Track): TrackDesc = {
      ALL.find(_.id == t.id).head
    }
  }

  object ConferenceRooms {

    // Tip : I use the ID to sort-by on the view per day... So if the exhibition floor id is "aaa" it will be
    // the first column on the HTML Table
    val HALL_EXPO = Room("a_hall", "Exhibition floor", 1500, "special")

    val ROOM3 = Room("room3", "Room 3", 345, "theatre")
    val ROOM4 = Room("room4", "Room 4", 364, "theatre")
    val ROOM5 = Room("room5", "Room 5", 684, "theatre")
    val ROOM6 = Room("room6", "Room 6", 407, "theatre")
    val ROOM7 = Room("room7", "Room 7", 407, "theatre")
    val ROOM8 = Room("room8", "Room 8", 745, "theatre")
    val ROOM9 = Room("room9", "Room 9", 425, "theatre")

    val BOF1 = Room("bof1", "BOF 1", 70, "classroom")
    val BOF2 = Room("bof2", "BOF 2", 70, "classroom")

    val allRoomsUni = List(ROOM4, ROOM5, ROOM8, ROOM9)

    val allRoomsTIA = List(ROOM4, ROOM5, ROOM8, ROOM9)

    val keynoteRoom = List(ROOM8)

    val allRoomsConf = List(ROOM8, ROOM5, ROOM9, ROOM6, ROOM7, ROOM4, ROOM3)
    val fridayRoomsConf = List(ROOM4, ROOM5, ROOM8, ROOM9)

    val allRoomsLabs = List(BOF1, BOF2)
    val oneRoomLabs = List(BOF1)

    val allRoomsBOF = List(BOF1, BOF2)
    val oneRoomBOF = List(BOF1)

    val allRooms = List(ROOM8, ROOM5, ROOM9, ROOM6, ROOM7, ROOM4, ROOM3, BOF1, BOF2, HALL_EXPO)
  }

  object ConferenceSlotBreaks {
    val registration = SlotBreak("reg", "Registration, Welcome and Breakfast", "Accueil", ConferenceRooms.HALL_EXPO)
    val petitDej = SlotBreak("dej", "Breakfast", "Accueil et petit-déjeuner", ConferenceRooms.HALL_EXPO)
    val coffee = SlotBreak("coffee", "Coffee Break", "Pause café", ConferenceRooms.HALL_EXPO)
    val lunch = SlotBreak("lunch", "Lunch", "Pause déjeuner", ConferenceRooms.HALL_EXPO)
    val shortBreak = SlotBreak("chgt", "Break", "Pause courte", ConferenceRooms.HALL_EXPO)
    val exhibition = SlotBreak("exhib", "Exhibition", "Exhibition", ConferenceRooms.HALL_EXPO)
    val meetAndGreet = SlotBreak("meet", "Meet & Greet (Exhibition) - Evening Keynote 19:00-19:30", "Exhibition", ConferenceRooms.HALL_EXPO)
    val eveningKeynote = SlotBreak("evKey", "Evening Keynote", "Keynote", ConferenceRooms.ROOM8)
    val closingKeynote = SlotBreak("closeKey", "Closing Keynote", "Keynote", ConferenceRooms.ROOM8)
  }

  object ConferenceSlots {

    // UNIVERSITY
    // TODO create a val for each ProposalType - See Devoxx BE as a complete (and complex sample)

    // TOOLS IN ACTION
    // TODO create a val for each ProposalType - See Devoxx BE as a complete (and complex sample)

    // HANDS ON LABS
    // TODO create a val for each ProposalType - See Devoxx BE as a complete (and complex sample)

    // BOFS
    // TODO create a val for each ProposalType - See Devoxx BE as a complete (and complex sample)

    val bofSlotWednesday: List[Slot] = {

      val bofWednesdayEveningSlot1 = ConferenceRooms.oneRoomBOF.map {
        r1 =>
          SlotBuilder(ConferenceProposalTypes.BOF.id, "wednesday", new DateTime("2014-11-14T10:45:00.000+01:00").toDateTime(DateTimeZone.forID("Europe/Brussels")), new DateTime("2014-11-14T11:45:00.000+01:00").toDateTime(DateTimeZone.forID("Europe/Brussels")), r1)
      }
      bofWednesdayEveningSlot1
    }

    // QUICKIES

    val quickiesSlotsWednesday: List[Slot] = {

      val quickiesMondayLunch1 = ConferenceRooms.allRoomsConf.map {
        r1 =>
          SlotBuilder(ConferenceProposalTypes.QUICK.id, "monday", new DateTime("2014-11-12T13:10:00.000+01:00").toDateTime(DateTimeZone.forID("Europe/Brussels")), new DateTime("2014-11-12T13:25:00.000+01:00").toDateTime(DateTimeZone.forID("Europe/Brussels")), r1)
      }
      val quickiesMondayLunch2 = ConferenceRooms.allRoomsConf.map {
        r2 =>
          SlotBuilder(ConferenceProposalTypes.QUICK.id, "monday", new DateTime("2014-11-12T13:35:00.000+01:00").toDateTime(DateTimeZone.forID("Europe/Brussels")), new DateTime("2014-11-12T13:50:00.000+01:00").toDateTime(DateTimeZone.forID("Europe/Brussels")), r2)
      }
      quickiesMondayLunch1 ++ quickiesMondayLunch2
    }

    // CONFERENCE KEYNOTES

    // TODO here
    val keynoteSlotsWedneday: List[Slot] = {

      val keynoteWednesdaySlot2 = ConferenceRooms.keynoteRoom.map {
        r2 =>
          SlotBuilder(ConferenceProposalTypes.KEY.id, "wednesday",
            new DateTime("2014-11-12T10:15:00.000+01:00").toDateTime(DateTimeZone.forID("Europe/Brussels")),
            new DateTime("2014-11-12T10:55:00.000+01:00").toDateTime(DateTimeZone.forID("Europe/Brussels")), r2)
      }
      val keynoteWednesdaySlot3 = ConferenceRooms.keynoteRoom.map {
        r3 =>
          SlotBuilder(ConferenceProposalTypes.KEY.id, "wednesday",
            new DateTime("2014-11-12T10:55:00.000+01:00").toDateTime(DateTimeZone.forID("Europe/Brussels")),
            new DateTime("2014-11-12T11:30:00.000+01:00").toDateTime(DateTimeZone.forID("Europe/Brussels")), r3)
      }
      val keynoteWednesdaySlot4 = ConferenceRooms.keynoteRoom.map {
        r4 =>
          SlotBuilder(ConferenceProposalTypes.KEY.id, "wednesday", new DateTime("2014-11-12T19:00:00.000+01:00").toDateTime(DateTimeZone.forID("Europe/Brussels")), new DateTime("2014-11-12T19:30:00.000+01:00").toDateTime(DateTimeZone.forID("Europe/Brussels")), r4)
      }

      keynoteWednesdaySlot2 ++ keynoteWednesdaySlot3 ++ keynoteWednesdaySlot4
    }

    // CONFERENCE SLOTS
    // TODO create a val for each Conference Day - See Devoxx BE as a complete (and complex sample)

    val conferenceSlotsWedneday: List[Slot] = {

      val conferenceWednesdaySlot1 = ConferenceRooms.allRoomsConf.map {
        r1 =>
          SlotBuilder(ConferenceProposalTypes.CONF.id, "wednesday", new DateTime("2014-11-12T12:00:00.000+01:00").toDateTime(DateTimeZone.forID("Europe/Brussels")), new DateTime("2014-11-12T13:00:00.000+01:00").toDateTime(DateTimeZone.forID("Europe/Brussels")), r1)
      }
      val conferenceWednesdaySlot2 = ConferenceRooms.allRoomsConf.map {
        r2 =>
          SlotBuilder(ConferenceProposalTypes.CONF.id, "wednesday", new DateTime("2014-11-12T14:00:00.000+01:00").toDateTime(DateTimeZone.forID("Europe/Brussels")), new DateTime("2014-11-12T15:00:00.000+01:00").toDateTime(DateTimeZone.forID("Europe/Brussels")), r2)
      }
      val conferenceWednesdaySlot3 = ConferenceRooms.allRoomsConf.map {
        r3 =>
          SlotBuilder(ConferenceProposalTypes.CONF.id, "wednesday", new DateTime("2014-11-12T15:10:00.000+01:00").toDateTime(DateTimeZone.forID("Europe/Brussels")), new DateTime("2014-11-12T16:10:00.000+01:00").toDateTime(DateTimeZone.forID("Europe/Brussels")), r3)
      }
      val conferenceWednesdaySlot4 = ConferenceRooms.allRoomsConf.map {
        r4 =>
          SlotBuilder(ConferenceProposalTypes.CONF.id, "wednesday", new DateTime("2014-11-12T16:40:00.000+01:00").toDateTime(DateTimeZone.forID("Europe/Brussels")), new DateTime("2014-11-12T17:40:00.000+01:00").toDateTime(DateTimeZone.forID("Europe/Brussels")), r4)
      }
      val conferenceWednesdaySlot5 = ConferenceRooms.allRoomsConf.map {
        r5 =>
          SlotBuilder(ConferenceProposalTypes.CONF.id, "wednesday", new DateTime("2014-11-12T17:50:00.000+01:00").toDateTime(DateTimeZone.forID("Europe/Brussels")), new DateTime("2014-11-12T18:50:00.000+01:00").toDateTime(DateTimeZone.forID("Europe/Brussels")), r5)
      }
      conferenceWednesdaySlot1 ++ conferenceWednesdaySlot2 ++ conferenceWednesdaySlot3 ++ conferenceWednesdaySlot4 ++ conferenceWednesdaySlot5
    }


    // Registration, coffee break, lunch etc
    val wednesdayBreaks = List(
      SlotBuilder(ConferenceSlotBreaks.registration, "wednesday", new DateTime("2014-11-12T08:00:00.000+01:00").toDateTime(DateTimeZone.forID("Europe/Brussels")), new DateTime("2014-11-12T09:30:00.000+01:00"))
      , SlotBuilder(ConferenceSlotBreaks.coffee, "wednesday", new DateTime("2014-11-12T11:40:00.000+01:00").toDateTime(DateTimeZone.forID("Europe/Brussels")), new DateTime("2014-11-12T12:00:00.000+01:00"))
      , SlotBuilder(ConferenceSlotBreaks.lunch, "wednesday", new DateTime("2014-11-12T13:00:00.000+01:00").toDateTime(DateTimeZone.forID("Europe/Brussels")), new DateTime("2014-11-12T14:00:00.000+01:00"))
      , SlotBuilder(ConferenceSlotBreaks.coffee, "wednesday", new DateTime("2014-11-12T16:10:00.000+01:00").toDateTime(DateTimeZone.forID("Europe/Brussels")), new DateTime("2014-11-12T16:40:00.000+01:00"))
      , SlotBuilder(ConferenceSlotBreaks.meetAndGreet, "wednesday", new DateTime("2014-11-12T18:30:00.000+01:00").toDateTime(DateTimeZone.forID("Europe/Brussels")), new DateTime("2014-11-12T20:00:00.000+01:00"))
    )
    val thursdayBreaks = List(
      SlotBuilder(ConferenceSlotBreaks.petitDej, "thursday", new DateTime("2014-11-13T08:00:00.000+01:00").toDateTime(DateTimeZone.forID("Europe/Brussels")), new DateTime("2014-11-13T09:30:00.000+01:00"))
      , SlotBuilder(ConferenceSlotBreaks.coffee, "thursday", new DateTime("2014-11-13T10:30:00.000+01:00").toDateTime(DateTimeZone.forID("Europe/Brussels")), new DateTime("2014-11-13T10:50:00.000+01:00"))
      , SlotBuilder(ConferenceSlotBreaks.lunch, "thursday", new DateTime("2014-11-13T13:00:00.000+01:00").toDateTime(DateTimeZone.forID("Europe/Brussels")), new DateTime("2014-11-13T14:00:00.000+01:00"))
      , SlotBuilder(ConferenceSlotBreaks.coffee, "thursday", new DateTime("2014-11-13T16:10:00.000+01:00").toDateTime(DateTimeZone.forID("Europe/Brussels")), new DateTime("2014-11-13T16:40:00.000+01:00"))
    )
    val fridayBreaks = List(
      SlotBuilder(ConferenceSlotBreaks.petitDej, "friday", new DateTime("2014-11-14T08:30:00.000+01:00").toDateTime(DateTimeZone.forID("Europe/Brussels")), new DateTime("2014-11-14T09:30:00.000+01:00"))
      , SlotBuilder(ConferenceSlotBreaks.coffee, "friday", new DateTime("2014-11-14T10:30:00.000+01:00").toDateTime(DateTimeZone.forID("Europe/Brussels")), new DateTime("2014-11-14T10:45:00.000+01:00"))
    )

    // Define for each day the list of Slots.

    val wednesday: List[Slot] = {
      wednesdayBreaks ++ keynoteSlotsWedneday ++ conferenceSlotsWedneday //++ quickiesSlotsWednesday ++ bofSlotsWednesday ++ labsSlotsWednesday
    }

    val thursday: List[Slot] = {
      thursdayBreaks // ++ keynoteSlotsThursday ++ conferenceSlotsThursday ++ quickiesSlotsThursday ++ bofSlotsThursday ++ labsSlotsThursday
    }

    val friday: List[Slot] = {
      fridayBreaks // ++ conferenceSlotsFriday ++ bofSlotFriday
    }

    // COMPLETE DEVOXX UK is the 3 days

    def all: List[Slot] = {
      wednesday ++ thursday ++ friday
    }
  }

  // TODO : please customize below.
  def current() = ConferenceDescriptor(
    eventCode = "DevoxxUK2015",
    // You will need to update conf/routes files with this code if modified
    confUrlCode = "devoxxuk2015",
    frLangEnabled = false,
    fromEmail = Play.current.configuration.getString("mail.from").getOrElse("program@devoxx.com"),
    committeeEmail = Play.current.configuration.getString("mail.committee.email").getOrElse("program@devoxx.com"),
    bccEmail = Play.current.configuration.getString("mail.bcc"),
    bugReportRecipient = Play.current.configuration.getString("mail.bugreport.recipient").getOrElse("nicolas.martignole@devoxx.fr"),
    conferenceUrls = ConferenceUrls(
      faq = "http://www.devoxx.uk/faq/",
      registration = "http://reg.devoxx.com",
      confWebsite = "http://www.devoxx.uk/",
      cfpHostname = Play.current.configuration.getString("cfp.hostname").getOrElse("cfp.devoxx.uk")
    ),
    timing = ConferenceTiming(
      datesI18nKey = "8 au 10 avril 2015",
      speakersPassDuration = 5,
      preferredDayEnabled = true,
      firstDayFr = "8 avril",
      firstDayEn = "april 8th",
      datesFr = "du 8 au 10 avril 2015",
      datesEn = "from 8th to 10th of April, 2015",
      cfpOpenedOn = DateTime.parse("2014-11-17T00:00:00+01:00"),
      cfpClosedOn = DateTime.parse("2015-01-17T23:59:59+01:00"),
      scheduleAnnouncedOn = DateTime.parse("2014-02-13T00:00:00+01:00")
    ),
    hosterName = "Clever-cloud", hosterWebsite = "http://www.clever-cloud.com/#DevoxxFR",
    hashTag = "#DevoxxFR",
    conferenceSponsor = ConferenceSponsor(showSponsorProposalCheckbox = true, sponsorProposalType = ConferenceProposalTypes.CONF)
    , List("en")
    , "London"
    , showQuestion = false
  )

  val isCFPOpen: Boolean = {
//    current().timing.cfpOpenedOn.isBeforeNow && current().timing.cfpClosedOn.isAfterNow
    true
  }

}
