package database;

public interface DatabaseListener
{
  public void recordAdded(DatabaseRecord r);

  public void recordRemoved(DatabaseRecord r);

  public void databaseLoaded();

  public void databaseSaved();

}
