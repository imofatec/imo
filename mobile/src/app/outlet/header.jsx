import React from 'react';
import { View, Text, Pressable } from 'react-native';
import { Ionicons } from '@expo/vector-icons';

export default function Header() {
    return (

        <View className="h-16 w-full flex-row items-center justify-between px-4 border-b border-custom-gray">
            <Pressable className='h-10 w-10 rounded-full flex items-center justify-center'>
                <Ionicons name="menu" size={20} color="white" />
            </Pressable>
            <Text className='text-white text-lg font-bold flex-row items-center justify-center'><Ionicons name="caret-forward-outline" size={20} color="white" />IMO</Text>
            <Pressable className='h-10 w-10 rounded-full flex items-center justify-center'>
                <Ionicons name="search" size={20} color="white" />
            </Pressable>
        </View>
    );
}