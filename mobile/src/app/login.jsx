import React from "react";
import { View, Text, TextInput, Pressable } from "react-native";
import { router } from "expo-router";
import { Ionicons } from '@expo/vector-icons';


export default function Login() {
  return (
    <View className="flex-1 justify-center px-6 bg-custom-primary">

      <Text className='text-white text-6xl text-center mb-20'>
        <Ionicons name="caret-forward-outline" size={56} color="white" />IMO
      </Text>


      <Text className="text-white text-xl font-bold mb-2">Email</Text>
      <TextInput
        placeholder="joao@email.com"
        placeholderTextColor="#aaa"
        className="bg-white/20 text-white px-4 py-3 rounded-2xl mb-16"
      />


      <Text className="text-white text-xl font-bold mb-2">Senha</Text>
      <TextInput
        placeholder="Coxinha123@"
        placeholderTextColor="#aaa"
        secureTextEntry
        className="bg-white/20 text-white px-4 py-3 rounded-2xl mb-16"
      />

      <Pressable className="bg-white py-3 rounded-full mb-6"
        onPress={() => router.replace("/home")}
      >
        <Text className="text-black text-2xl text-center font-bold">Entrar</Text>
      </Pressable>

      <Pressable onPress={() => router.push("/register")}>
        <Text className="text-gray-500 text-center mb-6">
          Esqueceu sua senha?{" "}</Text>
      </Pressable>


      <Pressable onPress={() => router.push("/register")}>
        <Text className="text-gray-300 text-center">
          Não tem conta?{" "}
          <Text className="text-blue-400 font-bold">Cadastre-se</Text>
        </Text>
      </Pressable>
    </View>
  );
}
