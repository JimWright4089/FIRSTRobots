using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml;

namespace DragAndDropTest
{
  public class BatteryInfoFile
  {
    public const string BATTERY_TAG = "battery";
    public const string RFID_TAG = "rfid";
    public const string ID_TAG = "id";
    public const string LAST_SEEN_TAG = "last";
    public const string BATTERIES_TAG = "batteries";
    public const string BATTERY_FILE_NAME = "batteries.xml";

    List<BatteryInfo> mList = new List<BatteryInfo>();
    static BatteryInfoFile mInstance;
    BatteryHistory mHistory = BatteryHistory.GetInstance();

    public static BatteryInfoFile GetInstance()
    {
      if (null == mInstance)
      {
        mInstance = new BatteryInfoFile();
      }
      return mInstance;
    }

    private BatteryInfoFile()
    {
      try
      {
        XmlDocument doc = new XmlDocument();
        doc.Load(BATTERY_FILE_NAME);

        XmlNode curNode = doc.FirstChild;
        XmlNode childNode = curNode.FirstChild;
        while (null != childNode)
        {
          if (BATTERY_TAG == childNode.Name)
          {
            BatteryInfo newBattery = new BatteryInfo(childNode);
            mList.Add(newBattery);
          }
          childNode = childNode.NextSibling;
        }
      }
      catch (Exception)
      {
      }
    }

    public BatteryInfo GetInfo(int rfid)
    {
      foreach (BatteryInfo checkBattery in mList)
      {
        if (rfid == checkBattery.RFID)
        {
          checkBattery.Saw();
          return checkBattery;
        }
      }
      BatteryInfo newBattery = new BatteryInfo(rfid);
      mHistory.Add(newBattery, BatteryHistory.STAT_NEW_BATTERY);
      mList.Add(newBattery);
      return newBattery;
    }

    public bool IsKnown(BatteryInfo battery)
    {
      foreach (BatteryInfo checkBattery in mList)
      {
        if (battery.RFID == checkBattery.RFID)
        {
          return true;
        }
      }
      return false;
    }

    public void Check()
    {
      foreach (BatteryInfo checkBattery in mList)
      {
        checkBattery.Check();
      }
    }

    public void Save()
    {
      XmlDocument doc = new XmlDocument();
      XmlNode newNode = doc.CreateNode("element", BATTERIES_TAG, "");

      foreach (BatteryInfo checkBattery in mList)
      {
        checkBattery.Save(newNode, doc);
      }
      doc.AppendChild(newNode);
      doc.Save(BATTERY_FILE_NAME);
    }
  }

  public class BatteryInfo
  {
    const int ID_UNKNOWN = -1;
    const int STALE_SECONDS = 15;

    int mRFID = ID_UNKNOWN;
    int mBatteryID = ID_UNKNOWN;
    DateTime mLastSeen = DateTime.MinValue;
    DateTime mCharged = DateTime.MinValue;
    bool mRemoved = false;
    BatteryHistory mBatteryHistory = BatteryHistory.GetInstance();
  

    public int RFID
    {
      get { return mRFID; }
    }
    public int ID
    {
      get { return mBatteryID; }
    }

    public BatteryInfo()
    {

    }

    public BatteryInfo(int rfid)
    {
      mRFID = rfid;
      mLastSeen = DateTime.Now;
    }

    public BatteryInfo(XmlNode node)
    {
      if (BatteryInfoFile.BATTERY_TAG == node.Name)
      {
        XmlAttributeCollection attributes = node.Attributes;
        for (int i = 0; i < attributes.Count; i++)
        {
          XmlAttribute attribute = attributes[i];
          if (BatteryInfoFile.ID_TAG == attribute.Name)
          {
            int.TryParse(attribute.Value, out mBatteryID);
          }
          if (BatteryInfoFile.RFID_TAG == attribute.Name)
          {
            int.TryParse(attribute.Value, out mRFID);
          }
          if (BatteryInfoFile.LAST_SEEN_TAG == attribute.Name)
          {
            DateTime.TryParse(attribute.Value, out mLastSeen);
          }
        }
      }
    }

    public void Remove()
    {
      mRemoved = true;
      mLastSeen = DateTime.Now;
    }

    public void Insert()
    {
      if(true == mRemoved)
      {
        mBatteryHistory.Add(this, BatteryHistory.STAT_BATTERY_SWAP);
      }
      else
      {
        mBatteryHistory.Add(this, BatteryHistory.STAT_BATTERY_INSERTED);
      }

      mRemoved = false;
      mLastSeen = DateTime.Now;
    }

    public void Charging()
    {
      mBatteryHistory.Add(this, BatteryHistory.STAT_BATTERY_CHARGING);
      mLastSeen = DateTime.Now;
    }

    public void Charged()
    {
      mBatteryHistory.Add(this, BatteryHistory.STAT_BATTERY_CHARGED);
      mLastSeen = DateTime.Now;
      mCharged = DateTime.Now;
    }

    public void Check()
    {
      if(true == mRemoved)
      {
        if (DateTime.Compare(mLastSeen.AddSeconds(STALE_SECONDS),DateTime.Now) <0)
        {
          mBatteryHistory.Add(this, BatteryHistory.STAT_BATTERY_REMOVED);
          mRemoved = false;
          mLastSeen = DateTime.Now;
        }
      }
    }

    public void Save(XmlNode node, XmlDocument doc)
    {
      XmlNode newNode = doc.CreateNode("element", BatteryInfoFile.BATTERY_TAG, "");
      XmlAttribute newAttrib = doc.CreateAttribute(BatteryInfoFile.ID_TAG);
      newAttrib.InnerText = mBatteryID.ToString();
      newNode.Attributes.Append(newAttrib);

      XmlAttribute newAttrib2 = doc.CreateAttribute(BatteryInfoFile.RFID_TAG);
      newAttrib2.InnerText = mRFID.ToString();
      newNode.Attributes.Append(newAttrib2);

      XmlAttribute newAttrib3 = doc.CreateAttribute(BatteryInfoFile.LAST_SEEN_TAG);
      newAttrib3.InnerText = mLastSeen.ToString();
      newNode.Attributes.Append(newAttrib3);
      node.AppendChild(newNode);
    }

    public void Saw()
    {
      mLastSeen = DateTime.Now;
    }

    public bool SetID(int ID)
    {
      if(ID_UNKNOWN == mBatteryID)
      {
        mBatteryID = ID;
        return true;
      }
      return false;
    }
  }
}
