import React, { useState, useRef, useEffect } from 'react';
import { View, Pressable, TextInput, Animated } from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import { useRouter } from "expo-router";
import { useDrawerAnimation } from "../../hooks/useDrawerAnimation";
import { set } from 'zod';

export default function SearchBar() {
    const [isOpen, setIsOpen] = useState(false);
    const { slideAnim, opacityAnim, closeWithAnimation } = useDrawerAnimation(isOpen);
    const [query, setQuery] = useState("");
    const router = useRouter();
    const inputRef = useRef(null);

    const handleClose = () => {
        closeWithAnimation(() => setIsOpen(false));
    };

    const handleSearch = () => {
        if (!query.trim()) return;
        handleClose();
        router.push({
            pathname: '/allCourses',
            params: { search: query.trim() },
        });
        setQuery("")
    };

    useEffect(() => {
        if (isOpen && inputRef.current) {
            setTimeout(() => {
                inputRef.current?.focus();
            }, 100);
        }
    }, [isOpen]);

    return (
        <>
            <Pressable
                className='h-10 w-10 rounded-full flex items-center justify-center'
                onPress={() => setIsOpen(true)}
            >
                <Ionicons name="search" size={20} color="white" />
            </Pressable>

            {isOpen && (
                <>
                    <Animated.View style={{ opacity: opacityAnim }} className="absolute top-0 left-0 w-screen h-screen z-10">
                        <Pressable className="w-full h-full bg-gray-800 opacity-50" onPress={handleClose} />
                    </Animated.View>

                    <Animated.View
                        style={{
                            transform: [{ translateX: Animated.multiply(slideAnim, -1) }],
                            opacity: opacityAnim,
                        }}
                        className="absolute top-0 left-0 right-0 z-30 p-4">
                        <TextInput
                            ref={inputRef}
                            value={query}
                            onChangeText={setQuery}
                            onSubmitEditing={handleSearch}
                            className="h-11 w-full bg-custom-primary rounded-full px-4 text-white border border-white"
                            placeholder="Pesquise por um curso..."
                            placeholderTextColor="#fff"
                            returnKeyType="search"
                        />
                        <Pressable className='absolute flex items-center justify-center top-[19px] right-6 h-8 w-8' onPress={handleClose}>
                            <Ionicons name="close" size={20} color="white" />
                        </Pressable>
                    </Animated.View>
                </>
            )}
        </>
    );
}
