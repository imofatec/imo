import React from 'react';
import { View, Text, Pressable } from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import Drawer from '../../components/header/drawer';
import SearchBar from '../../components/header/searchBar';

export default function Header() {
    return (

        <View className="h-16 w-full flex-row items-center justify-between px-4 border-b border-custom-gray">
            <Drawer />
            <Text className='text-white text-lg font-bold flex-row items-center justify-center'><Ionicons name="caret-forward-outline" size={20} color="white" />IMO</Text>
            <SearchBar/>
        </View>
    );
}