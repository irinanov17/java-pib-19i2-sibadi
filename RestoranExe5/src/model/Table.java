package model;

public class Table {
    public TableStatus tableStatus;

    public Table(TableStatus tableStatus) {
        this.tableStatus = tableStatus;
    }

    @Override
    public String toString() {
        String tableStatus = "";
        switch (this.tableStatus) {
            case BUYS:
                tableStatus = "занят";
                break;
            case FREE:
                tableStatus = "свободен";
                break;
            case RESERVED:
                tableStatus = "зарезервирован";
        }
        return "Столик " + tableStatus;
    }
}
