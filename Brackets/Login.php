<?php
    $con = mysqli_connect("localhost", "alswls97", "wls5484!", "alswls97");
    mysqli_query($con,'SET NAMES utf8');
 
    $userID = $_POST["userID"];
    $userPassword = $_POST["userPassword"];
    
    $statement = mysqli_prepare($con, "SELECT * FROM register WHERE userID = ? AND userPassword = ?");
    mysqli_stmt_bind_param($statement, "ss", $userID, $userPassword);
    mysqli_stmt_execute($statement);
 
 
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $userID, $userPassword, $userName, $userPhone);
 
    $response = array();
    $response["success"] = true;
 
    while(mysqli_stmt_fetch($statement)) {
        $response["success"] = true;
        $response["userID"] = $userID;
        $response["userPassword"] = $userPassword;
        $response["userName"] = $userName;
        $response["userPhone"] = $userPhone;        
    }
 
    echo json_encode($response);
?>