import react from "react";
import {View,Text} from "react-native";
import { Redirect } from "expo-router";
import { Tabs } from "expo-router";

export default function Index() {
  return (
    <View className="flex-1 justify-center items-center bg-custom-primary">
      <Text className="text-white">Home</Text>
    </View>
  );
}