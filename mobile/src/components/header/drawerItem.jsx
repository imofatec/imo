import React from 'react';
import { View, Text, Pressable } from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import {router } from 'expo-router';

export default function DrawerItem({ icon, label,route, onNavigate }) {
    const handlePress = () => {
        router.push(`/${route}`);
        if (onNavigate) onNavigate();
    };
    return (
        <Pressable className="flex-row items-center gap-4" onPress={handlePress}>
            <Ionicons name={icon} size={20} color="white" />
            <Text className="text-white text-lg">{label}</Text>
        </Pressable>
    );
}