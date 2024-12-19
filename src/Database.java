import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private final String URL = "jdbc:mysql://localhost:3306/networkplanDB";
    private final String USER = "networkplanUser";
    private final String PASSWORD = "Passwort123";



    public int getUserID(String username, String password) {
        String sql = "SELECT id FROM user WHERE username = ? AND password = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return -1;
    }


    public boolean isUsernameExist(String username) {
        String sql = "SELECT username FROM user WHERE username = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return false;
    }


    public void createNetworkplan(int userID, String networkplanName) {
        String sql = "INSERT INTO networkplan (user_id, name) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userID);
            preparedStatement.setString(2, networkplanName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }


    public int getNetwokrplanID(int userID, String networkplanName) {
        String sql = "SELECT id FROM networkplan WHERE user_id = ? AND name = ?";

        try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userID);
            preparedStatement.setString(2, networkplanName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
        } catch  (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return -1;
    }


    public void addProcessToNetworkplan(int networkplanID, String processName, int processDuration, int processNr) {
        String sql = "INSERT INTO process (networkplan_id, name, duration, nr) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, networkplanID);
            preparedStatement.setString(2, processName);
            preparedStatement.setInt(3, processDuration);
            preparedStatement.setInt(4, processNr);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("Test");
        }
    }

    public int getProcessID (int networkplanID, String processName) {
        String sql = "SELECT id FROM process WHERE networkplan_id = ? AND name = ?";

        try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, networkplanID);
            preparedStatement.setString(2, processName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
        } catch  (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return -1;
    }


    public void addSuccessorToProcess(int processID, int successorID) {
        String sql = "INSERT INTO successor (process_id, successor_id) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, processID);
            preparedStatement.setInt(2, successorID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }


    public void addDependenciesToProcess(int processID, int dependencyID) {
        String sql = "INSERT INTO dependencies (process_id, dependency_id) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, processID);
            preparedStatement.setInt(2, dependencyID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }


    public void changeProcessName(int processID, String newProcessName) {
        String sql = "UPDATE process SET name = ? WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, newProcessName);
            preparedStatement.setInt(2, processID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }


    public void deleteDependencies(int processID) {
        String sql = "DELETE FROM dependencies WHERE process_id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, processID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }


    public void changeProcessDuration(int processID, int newDuration) {
        String sql = "UPDATE process SET duration = ? WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, newDuration);
            preparedStatement.setInt(2, processID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }


    public void deleteSelectedProcessFromSuccessor(int processID, int selectedProcessID) {
        String sql = "DELETE FROM successor WHERE process_id = ? AND successor_id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, processID);
            preparedStatement.setInt(2, selectedProcessID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    public List<Networkplan> getAllNetworkplans(int userID) {
        String sql = "SELECT id, name FROM networkplan WHERE user_id = ?";
        List<Networkplan> listOfNetworkplan = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String networkplanName = resultSet.getString("name");
                    int networkplanID = resultSet.getInt("id");

                    listOfNetworkplan.add(new Networkplan(networkplanName, networkplanID));
                }
                return listOfNetworkplan;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return new ArrayList<>();
    }


    public List<Process> getAllProcesses(int networkplanID) {
        String sql = "SELECT * FROM process WHERE networkplan_id = ?";
        List<Process> listOfProcess = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, networkplanID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String processName = resultSet.getString("name");
                    int processNr = resultSet.getInt("nr");
                    int processDuration = resultSet.getInt("duration");
                    int processID = resultSet.getInt("id");

                    listOfProcess.add(new Process(processName, processNr, processDuration, processID));
                }
                return listOfProcess;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return new ArrayList<>();
    }


    public List<Integer> getAllSuccessor(int processID) {
        String sql = "SELECT successor_id FROM successor WHERE process_id = ?";
        List<Integer> listOfProcessIDs = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, processID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int dependencyID = resultSet.getInt("successor_id");
                    listOfProcessIDs.add(dependencyID);
                }
                return listOfProcessIDs;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return new ArrayList<>();
    }


    public List<Integer> getAllDependencies(int processID) {
        String sql = "SELECT dependency_id FROM dependencies WHERE process_id = ?";
        List<Integer> listOfProcessIDs = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, processID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int dependencyID = resultSet.getInt("dependency_id");
                    listOfProcessIDs.add(dependencyID);
                }
                return listOfProcessIDs;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return new ArrayList<>();
    }


    public void deleteNetworkplanFromDatabase(int networkplanID) {
        String sql = "DELETE FROM networkplan WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, networkplanID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }


    public int createAccount(String username, String password) {
        String sql = "INSERT INTO user (username, password) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
            try (ResultSet resultSetGeneratedKey = preparedStatement.getGeneratedKeys()) {
                if (resultSetGeneratedKey.next()) {
                    return resultSetGeneratedKey.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return -1;
    }

    public String getUsername(int userID) {
        String sql = "SELECT username FROM user WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("username");
                }
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return "Hacker";
    }
}
