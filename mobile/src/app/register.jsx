import React from "react";
import { View, Text, TextInput, Pressable } from "react-native";
import { router } from "expo-router";
import { Ionicons } from '@expo/vector-icons';


export default function Register() {
  return (
    <View className="flex-1 justify-center px-6 bg-custom-primary">

      <Text className='text-white text-6xl text-center mb-20'>
        <Ionicons name="caret-forward-outline" size={56} color="white" />IMO
      </Text>

      <Text className="text-white text-xl font-bold mb-2">Email</Text>
      <TextInput
        placeholder="joao@email.com"
        placeholderTextColor="#aaa"
        className="bg-white/20 text-white px-4 py-3 rounded-2xl mb-8"
      />


      <Text className="text-white text-xl font-bold mb-2">Usuário</Text>
      <TextInput
        placeholder="JoaoSilva"
        placeholderTextColor="#aaa"
        className="bg-white/20 text-white px-4 py-3 rounded-2xl mb-8"
      />


      <Text className="text-white text-xl font-bold mb-2">Senha</Text>
      <TextInput
        placeholder="Coxinha123@"
        placeholderTextColor="#aaa"
        secureTextEntry
        className="bg-white/20 text-white px-4 py-3 rounded-2xl mb-8"
      />


      <Text className="text-white text-xl font-bold mb-2">Confirmar senha</Text>
      <TextInput
        placeholder="Coxinha123@"
        placeholderTextColor="#aaa"
        secureTextEntry
        className="bg-white/20 text-white px-4 py-3 rounded-2xl mb-16"
      />


      <Pressable className="bg-white py-3 rounded-full mb-6" 
      onPress={() => router.push("/login")}>
        <Text className="text-black text-2xl text-center font-bold">Cadastrar</Text>
      </Pressable>

      <Pressable onPress={() => router.push("/login")}>
        <Text className="text-gray-300 text-center">
          Já possui conta?{" "}
          <Text className="text-blue-400 font-bold">Login</Text>
        </Text>
      </Pressable>
    </View>
  );
}
